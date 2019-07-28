import * as React from "react";
import { Theme } from "@material-ui/core";
import { ChangeEvent } from "react";
import validate, { Constraints, revalidateField, ValidationResult } from "../utils/validation";
import moment = require("moment");

export interface AbstractModel {
  id: number,
  [key:string]:any
}

export interface AbstractFormProps<TModel> {
  onSubmit: (data: TModel) => void
  onDelete?: (data: TModel) => void
  onCancel: () => void
  model: TModel
  theme: Theme
  className?: string
  constraints?: Constraints
}

export interface AbstractFormState<TModel> {
  editingModel?: TModel,
  validationResult: ValidationResult
}

export type FieldChangeHandler = (
  fieldName: string,
  e: ChangeEvent<HTMLInputElement|HTMLSelectElement|HTMLTextAreaElement> | null,
  value?: any
) => void

export class AbstractForm<TModel extends AbstractModel, TProps extends AbstractFormProps<TModel> = AbstractFormProps<TModel>, TState extends AbstractFormState<TModel> = AbstractFormState<TModel>>
  extends React.PureComponent<AbstractFormProps<TModel> & TProps, AbstractFormState<TModel> & TState>
{
  constructor(props: any){
    super(props);

    this.onSave = this.onSave.bind(this)
  }

  formNameSpace = 'Form';

  state = {
    validationResult: {errors: {}, success: true}
  } as AbstractFormState<TModel> & TState;

  componentDidUpdate(prevProps: any): void {
    if(prevProps.model.id !== this.props.model.id){
      this.updateEditingModel()
    }
  }

  componentDidMount(): void {
    this.updateEditingModel()
  }

  updateEditingModel(callback?: () => void){
    const editingModel: TModel = {...this.props.model};
    this.setState({ editingModel: editingModel }, callback)
  };

  onFieldChange: FieldChangeHandler = (
    fieldName: string,
    e: ChangeEvent<HTMLInputElement|HTMLSelectElement|HTMLTextAreaElement> | null,
    value?: any,
    callback?: () => void
  ) => {
    if(!this.state.editingModel) return;

    const newValue = (e && e.target) ? e.target.value : value;

    const newPartialState: any = {
      editingModel: {
        ...this.state.editingModel,
        [fieldName]: newValue
      } as TModel
    };

    if(this.props.constraints){
      newPartialState.validationResult = revalidateField(
        this.state.validationResult,
        this.props.constraints as Constraints,
        fieldName,
        newValue
      )
    }

    this.setState(newPartialState, callback)
  };

  onDateChange = (fieldName: string, date: any, callback?: () => void) => {
    return this.onFieldChange(fieldName, null, moment(date).utc(true).toDate(), callback)
  };

  onTimeChange = (fieldName: string, date: any, callback?: () => void) => {
    return this.onFieldChange(fieldName, null, moment(date).toDate(), callback)
  };

  onBooleanChange = (fieldName: string, e: any, callback?: () => void) => {
    return this.onFieldChange(fieldName, null, e.target.value === 'true', callback)
  };

  validateModel = () => {
    if(this.props.constraints){
      const validationResult = validate(this.state.editingModel, this.props.constraints as Constraints);
      if(!validationResult.success){
        this.setState({ validationResult });
      }
      return validationResult.success;
    } else return true;
  };

  public onSave() {
    if(!this.state.editingModel) return;

    if(!this.validateModel()) return;

    this.props.onSubmit(this.state.editingModel)
  }

  onDelete = () => {
    if(!this.state.editingModel || !this.props.onDelete) return;

    this.props.onDelete(this.props.model)
  };
}
