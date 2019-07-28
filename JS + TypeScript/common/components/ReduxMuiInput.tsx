import * as React from 'react';
import { Field, BaseFieldProps, WrappedFieldProps } from 'redux-form';

import TextField from '@material-ui/core/TextField';
import Checkbox from '@material-ui//core/Checkbox';

export type MuiFieldProps = WrappedFieldProps & { type: string, inputProps?: any }

const renderField = (field: MuiFieldProps) => {
  if (field.type == "boolean" || field.type == "checkbox") {
    const {floatingLabelText, label, fullWidth, ...restInputProps} = field.inputProps;
    return (
      <div>
        <Checkbox
          {...field.input}
          {...restInputProps}
          checked={!!field.input.value}
          onCheck={field.input.onChange}
          label={floatingLabelText || label}
        />
        {field.meta.error && (
          <div className="context-color_error">
            {field.meta.error}
          </div>
        )}
      </div>
    )
  }
  return (
    <TextField variant="outlined" InputLabelProps={{ shrink: true }}
      {...field.input}
      {...field.inputProps}
      type={field.type}
      errorText={field.meta.error}
    />
  );
};

export default class ReduxMuiInput extends React.Component<BaseFieldProps & { inputProps: any, type: string }> {
  render() {
    return (
      <Field
        {...this.props}
        component={renderField}
      />
    );
  }
}
