import * as React from 'react';
import Drawer from '@material-ui/core/Drawer';
import Typography from '@material-ui/core/Typography';
import MenuItem from '@material-ui/core/MenuItem';
import AppBar from '@material-ui/core/AppBar';
import * as ApplicationHeaderStore from '../store/ApplicationHeader';
import * as ApplicationStore from '../store/Application';
import { RouteComponentProps } from "react-router";
import { IconButton } from "@material-ui/core";
import Toolbar from "@material-ui/core/Toolbar";
import MenuIcon from "@material-ui/icons/Menu";
import Button from "@material-ui/core/Button";
import Tab from "@material-ui/core/Tab";
import Tabs from "@material-ui/core/Tabs";

function LinkTab(props: any) {
  return <Tab
    component="a"
    onClick={event => event.preventDefault()}
    style={{height: 64}}
    {...props}
  />;
}

// At runtime, Redux will merge together...
type ApplicationHeaderProps =
  ApplicationHeaderStore.ApplicationHeaderState
  & typeof ApplicationHeaderStore.actionCreators
  & ApplicationStore.ApplicationState
  & typeof ApplicationStore.actionCreators
  & RouteComponentProps<{}>;

class NavMenu extends React.Component<ApplicationHeaderProps, { open: boolean }> {

  constructor(props: ApplicationHeaderProps) {
    super(props);
    this.state = {
      open: false
    };
  }

  toggleOpen = () => this.setState({open: !this.state.open});

  public render() {
    return (
      <div className="print-hidden">
        <div style={{height: '64px'}}>
          <AppBar
            style={{
              background: 'linear-gradient(270deg, #2196F3 20.44%, #00CCBE 100%)',
              color: 'white'
            }}
          >
            <Toolbar>
              <IconButton aria-label="Menu" color="inherit" onClick={this.toggleOpen}>
                <MenuIcon color="inherit"/>
              </IconButton>
              <div className="flex-container flex-container--centered flex-item--greedy">
                <div className="flex-item--fixed">
                  <div style={{display: 'flex'}}>
                    {this.props.headerText &&
                    <span className="ph+">
                      {this.props.headerText.toLowerCase()}
                    </span>
                    }
                    <b className="">
                    </b>
                  </div>
                </div>
                <div className="flex-item--flexible text-align--right">
                  <Typography variant="h5" color="inherit">
                  </Typography>
                </div>
                <div className="flex-item--fixed text-align--left ml+">
                  <Typography variant="body2" color="inherit">

                  </Typography>
                </div>
                <div className="flex-item--greedy flex-container flex-container--centered flex-container--vertical">
                </div>
              </div>
            </Toolbar>
          </AppBar>
          <Drawer
            open={this.state.open}
            onClose={() => this.setState({open: false})}
          >
            <MenuItem
              onClick={() => {
                this.props.push('/');
                this.toggleOpen();
              }}
            >
              ЛК
            </MenuItem>
          </Drawer>
        </div>
      </div>
    );
  }
}

export default NavMenu;
