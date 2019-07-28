import * as React from "react";
import { AbstractForm, AbstractFormProps, AbstractFormState, AbstractModel, FieldChangeHandler } from "./AbstractForm";
import TextField from "@material-ui/core/TextField";
import FormControl from "@material-ui/core/FormControl";
import FormHelperText from "@material-ui/core/FormHelperText";
import translations from "../translation/i18n";
import OutlinedInput from "@material-ui/core/OutlinedInput/OutlinedInput";
import { MenuItem } from "@material-ui/core";
import Select from "@material-ui/core/Select";
import { NamedTo } from "../models/Schema";
import InputLabel from "@material-ui/core/InputLabel";

export interface MuiInputFieldProps {
  value: string | number
  name: string
  label: string
  error?: string
  onChange: FieldChangeHandler
  className?: string
}

export class MuiInputField extends React.PureComponent<MuiInputFieldProps>{
  onChange = (e: any) => {
    this.props.onChange(this.props.name, e)
  };

  render(){
    return(
      <FormControl variant="outlined" fullWidth className={this.props.className} error={!!this.props.error}>
        <TextField
          error={!!this.props.error}
          variant="outlined"
          InputLabelProps={{shrink: true}}
          label={this.props.label}
          value={this.props.value}
          onChange={this.onChange}
        />
        {this.props.error && (
          <FormHelperText>
            {this.props.error}
          </FormHelperText>
        )}
      </FormControl>
    )
  }
}

export class MUIForm<TModel extends AbstractModel, TProps extends AbstractFormProps<TModel> = AbstractFormProps<TModel>, TState extends AbstractFormState<TModel> = AbstractFormState<TModel>> extends AbstractForm<TModel, TProps, TState>
{
  renderInputField = (name: keyof TModel & string, label: string, className?: string) => {
    return (
      <MuiInputField
        name={name}
        value={this.state.editingModel![name]}
        label={label}
        onChange={this.onFieldChange}
        error={translations[this.formNameSpace + '.' + this.state.validationResult.errors[name]]}
        className={className}
      />
    )
  };

  renderSelectField = (
    name: keyof TModel & string,
    label: string,
    variants: NamedTo[],
    options?: {className?: string, displayEmpty?: boolean, emptyText?: string, labelWidth?: number}
  ) => {
    if(!options) options = {labelWidth: 180};

    const id = `select-${this.state.editingModel!.id}-${name}`;

    return(
      <FormControl variant="outlined" error={!!this.state.validationResult.errors[name]} fullWidth>
        {label && <InputLabel htmlFor={id}>{label}</InputLabel>}
        <Select
          variant="outlined"
          displayEmpty={options && options.displayEmpty}
          value={this.state.editingModel![name] || '-1'}
          onChange={this.onFieldChange.bind(null, name)}
          input={<OutlinedInput name={name} labelWidth={options.labelWidth as number} id={id}/>}
        >
          {options.emptyText && <MenuItem value="-1" disabled>
            {options.emptyText}
          </MenuItem>}
          {variants
            .map(v => (
              <MenuItem key={v.id} value={v}>
                {v.name}
              </MenuItem>
            ))
          }
        </Select>
        {this.state.validationResult.errors[name] && (
          <FormHelperText>
            {translations[this.formNameSpace + '.' + this.state.validationResult.errors[name]]}
          </FormHelperText>
        )}
      </FormControl>
    )
  }
}
