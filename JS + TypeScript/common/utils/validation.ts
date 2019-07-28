export const ERROR_CODES_SEPARATOR = '; ';
export const VALIDATION_NAMES_SEPARATOR = ' ';
export const ERROR_NAMES = {
  FIELD_IS_REQUIRED_ERROR: 'fieldIsRequiredError',
  VALUE_SHOULD_BE_MORE_THAN: 'valueShouldBeMoreThanError',
  VALUE_SHOULD_BE_LESS_THAN: 'valueShouldBeLessThanError',
  VALUE_SHOULD_BE_MORE_THAN_OR_EQUAL_TO: 'valueShouldBeMoreThanOrEqualToError',
  VALUE_SHOULD_BE_LESS_THAN_OR_EQUAL_TO: 'valueShouldBeLessThanOrEqualToError',
  VALUE_IS_NOT_VALID_JSON: 'valueIsNotValidJSONError',
};
export type Validator = (key: string, data: any, validationContext: any) => boolean | string
export type Constraints = {[key: string]: string | Function | {[key: string]: any}};
export type ValidationResult = {errors: {[key: string]: string}, success: boolean};

export const json: Validator = (key, data) => {
  const rawValue = data[key];
  try {
    JSON.parse(rawValue);
    return false;
  } catch (e) {
    return ERROR_NAMES.VALUE_IS_NOT_VALID_JSON;
  }
};

export const required: Validator = (key, data) => {
  const value = data[key];

  switch(typeof(value)){
    case 'string':
      if (value && (value.length !== undefined && value.length !== 0)) {
        return false;
      }
      break;

    case 'object':
      if (value !== undefined && value !== null) {
        return false;
      }
      break;

    case 'number':
      if (value !== undefined && !Number.isNaN(value)) {
        return false;
      }
      break;
  }

  return ERROR_NAMES.FIELD_IS_REQUIRED_ERROR;
};

export const moreThan: Validator = (key, data, validationContext) => {

  switch(typeof(validationContext)){
    case 'number':
      if (data[key] > validationContext) {
        return false;
      }
      break;
    default: throw new Error('Unsupported validation context type.')
  }

  return ERROR_NAMES.VALUE_SHOULD_BE_MORE_THAN;
};

export const moreThanOrEqualTo: Validator = (key, data, validationContext) => {

  switch(typeof(validationContext)){
    case 'number':
      if (data[key] >= validationContext) {
        return false;
      }
      break;
    default: throw new Error('Unsupported validation context type.')
  }

  return ERROR_NAMES.VALUE_SHOULD_BE_MORE_THAN_OR_EQUAL_TO;
};

export const lessThan: Validator = (key, data, validationContext) => {

  switch(typeof(validationContext)){
    case 'number':
      if (data[key] < validationContext) {
        return false;
      }
      break;
    default: throw new Error('Unsupported validation context type.')
  }

  return ERROR_NAMES.VALUE_SHOULD_BE_LESS_THAN;
};

export const lessThanOrEqualTo: Validator = (key, data, validationContext) => {

  switch(typeof(validationContext)){
    case 'number':
      if (data[key] <= validationContext) {
        return false;
      }
      break;
    default: throw new Error('Unsupported validation context type.')
  }

  return ERROR_NAMES.VALUE_SHOULD_BE_LESS_THAN_OR_EQUAL_TO;
};

export const validators: {[key: string]: Validator} = {
  json,
  required,
  moreThan,
  moreThanOrEqualTo,
  lessThan,
  lessThanOrEqualTo,
};

export function applyValidator(fieldName: string, validator: Validator, data: any, validationContext: any){
  const validationError = validator(fieldName, data, validationContext);
  if(!validationError) return;

  return validationError as string;
}

export default function validate(data: any, fields: Constraints): ValidationResult {
  const result: ValidationResult = { errors: {}, success: true };
  for (let fieldName in fields) {
    if (!fields.hasOwnProperty(fieldName)) {
      continue;
    }

    if(typeof(fields[fieldName]) === 'object'){
      const validatorsNames = Object.keys(fields[fieldName]);

      validatorsNames.forEach((fieldValidatorName: any) => {
        const validationContext = (fields[fieldName] as any)[fieldValidatorName];
        const validator = typeof(fieldValidatorName) === 'function' ? fieldValidatorName : validators[fieldValidatorName];
        if (!validator) {
          console.warn(`Missing validator with name ${fieldValidatorName}.`);
          return false;
        }
        const validatorError = applyValidator(fieldName, validator, data, validationContext);
        if (validatorError) {
          result.errors[fieldName] = result.errors[fieldName]
            ? result.errors[fieldName] + ERROR_CODES_SEPARATOR + validatorError
            : validatorError;
        }
      });
    } else if(typeof(fields[fieldName]) === 'function'){
      const validatorError = applyValidator(fieldName, (fields[fieldName] as Validator), data, true);
      if (validatorError) {
        result.errors[fieldName] = result.errors[fieldName]
          ? result.errors[fieldName] + ERROR_CODES_SEPARATOR + validatorError
          : validatorError;
      }
    } else if(typeof(fields[fieldName]) === 'string'){
      const validatorsNames = (fields[fieldName] as string).split(VALIDATION_NAMES_SEPARATOR);

      validatorsNames.forEach(n => {
        const validator = validators[n];
        if (!validator) {
          console.warn(`Missing validator with name ${n}.`);
          return false;
        }
        const validatorError = applyValidator(fieldName, validator, data, true);
        if (validatorError) {
          result.errors[fieldName] = result.errors[fieldName]
            ? result.errors[fieldName] + ERROR_CODES_SEPARATOR + validatorError
            : validatorError;
        }
      });
    }
  }

  result.success = Object.keys(result.errors).length === 0;
  return result;
}

export const revalidateField = (validationResult: ValidationResult, constraints: Constraints, fieldName: string, value: any ) => {
  const newValidationResult = {...validationResult};
  const fieldValidationResult = validate({[fieldName]: value}, constraints as Constraints);
  newValidationResult.errors[fieldName] = fieldValidationResult.errors[fieldName];
  newValidationResult.success = Object.keys(newValidationResult.errors).length === 0;
  return newValidationResult;
};
