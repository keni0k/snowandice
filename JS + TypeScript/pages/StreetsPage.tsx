import React from "react";
import LeafletMap from "../common/components/LeafletMap";
import config from "../config";
import { Street } from "../models/Schema";
import { LatLngLiteral, LayerGroup, Polyline, LatLng, Marker, Popup } from "leaflet";
import { Button } from "@material-ui/core";
import { connect } from "react-redux";
import { RootState, streetsResourceStore } from "../store/";
import ResourceStore from "../common/ResourceStore";
import { StreetsApi } from "../store/StreetsApi";

export type StreetsPageProps =
  {
    streetsStore: ResourceStore<Street>
    api: StreetsApi
  } & typeof streetsResourceStore.actionCreators

export interface StreetsPageState {
}

export class StreetsPage extends React.PureComponent<StreetsPageProps, StreetsPageState>{
  state = {
  };

  componentDidMount(): void {
  }

  render(){
    return (
      <div>
      </div>
    )
  }
}

export default connect(
  (state: RootState) => ({
    streetsStore: state.streets,
    api: state.application.api
  }),
  {
    ...streetsResourceStore.actionCreators,
  }
)(StreetsPage) as typeof StreetsPage;
