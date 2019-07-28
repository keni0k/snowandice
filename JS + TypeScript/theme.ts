import { createMuiTheme } from "@material-ui/core/styles";
import { common } from "@material-ui/core/colors";
import { ThemeOptions } from "@material-ui/core/styles/createMuiTheme";

const basicTheme: ThemeOptions = {
  palette: {
    primary: {
      main: '#0D87E8',
      contrastText: common.white,
      dark: '#0D87E8',
      light: '#E3F2FD',
    },
    secondary: {
      main: '#00bfa5',
      contrastText: common.white,
    },
    common: {
      alert: '#E91E63'
    }
  },
  shape: {
    borderRadius: 16
  },
  overrides: {
    MuiOutlinedInput:{
      input: {
        padding: '10px 16px'
      }
    },
    MuiInputLabel: {
      shrink: {
        padding: '0 5px',
        backgroundColor: "white"
      }
    },
    MuiExpansionPanel: {
      root:{
        marginBottom: 8
      }
    },
    MuiTabs: {
      indicator: {
        height: 4
      }
    },
  },
  typography: {
    useNextVariants: true,
    fontFamily: 'Roboto',
    h1: {
      fontSize: 22,
      fontWeight: 500,
      lineHeight: 'normal',
      textTransform: 'none'
    },
    h2: {
      fontSize: 20,
      fontWeight: 500,
      lineHeight: 'normal',
      textTransform: 'none'
    },
    h4: {
      fontSize: 18,
      fontWeight: 400,
      lineHeight: '28px',
      textTransform: 'none'
    },
    h5: {
      fontSize: 17,
      fontWeight: 500,
      lineHeight: '28px',
      textTransform: 'none'
    },
    body1: {
      fontSize: 16,
      lineHeight: '23px',
      fontWeight: 'normal'
    },
    body2: {
      fontSize: 14,
      lineHeight: '20px',
      fontWeight: 'normal'
    },
  },
};

export const theme = createMuiTheme(basicTheme);


const darkThemeOptions: ThemeOptions = {
  overrides: {
    ...basicTheme.overrides,
    MuiInputLabel: {
      shrink: {
        padding: '0 5px',
        backgroundColor: "#333333"
      }
    },
  },
  palette: {
    ...basicTheme.palette,
    primary: {
      light: '#5c5c5c',
      dark: '#333333',
      main: '#0D87E8'
    },
    action: {
      selected: '#333333'
    },
    type: 'dark'
  },
};

export const darkTheme = createMuiTheme(
  {
    ...basicTheme,
    ...darkThemeOptions
  }
);

export default theme;
