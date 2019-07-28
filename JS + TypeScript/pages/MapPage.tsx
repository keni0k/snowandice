import React, { FormEvent } from "react";
import LeafletMap from "../common/components/LeafletMap";
import config from "../config";
import { LatLngLiteral, LayerGroup, Polyline, LatLng, Marker, Popup } from "leaflet";
import { Button } from "@material-ui/core";
import { ResourceState } from "../common/ResourceStore";
import { Street, Route } from "../models/Schema";
import { StreetsApi } from "../store/StreetsApi";
import { RootState, streetsResourceStore } from "../store";
import { connect } from "react-redux";
import TextField from "@material-ui/core/TextField";

type MapPageProps = {
  streetsStore: ResourceState<Street>
  api: StreetsApi
} & typeof streetsResourceStore.actionCreators

interface MapPageState {
  baseWayPoint: LatLng
  machinesCount: number
  routes: Route[]
}

export  class MapPage extends React.PureComponent<MapPageProps, MapPageState>{
  state: MapPageState = {
    baseWayPoint: new LatLng( 52.29420237796669, 104.29141044616699),
    machinesCount: 1,
    routes: []
  };

  leafletMap: LeafletMap | null;
  streetLayer = new LayerGroup();
  routesLayer = new LayerGroup();
  streets: Street[] = [];
  lastPoint?: LatLngLiteral ;
  updateStreets(){
    setTimeout(() => {
      if(!this.leafletMap || !this.leafletMap.map){
        return
      }
      this.streetLayer.clearLayers();
      this.leafletMap.map.setView(config.mapCenter, 13);
      this.leafletMap.map.addEventListener('click', e => {
        this.streets = [...this.streets, {name: '', clean: false, segments: [{start: this.lastPoint || this.state.baseWayPoint, end: e.latlng, width: 2}]}]
        this.lastPoint = e.latlng;
        console.log(JSON.stringify(this.streets));
      });

      this.props.streetsStore.array.forEach(streetSide => {
        streetSide.segments.forEach(segment => {
          const line = new Polyline([segment.start, segment.end], {
            color: 'red',
            weight: segment.width * 3,
            opacity: .4,
          });
          line.addTo(this.streetLayer)
        })
      });
      this.streetLayer.addTo(this.leafletMap.map);
      this.routesLayer.addTo(this.leafletMap.map);
    })
  }
  componentDidMount(): void {
    this.updateStreets();
  }
  componentDidUpdate(): void {
    /*this.updateStreets();*/
  }

  getDistanceFactor = (point: LatLng, street: Street) => {
    const streetStart = new LatLng(street.segments[0].start.lat, street.segments[0].start.lng);
    const streetEnd = new LatLng(street.segments[street.segments.length - 1].end.lat, street.segments[street.segments.length - 1].end.lng);
    const distanceStart = streetStart.distanceTo(point);
    const distanceEnd = streetEnd.distanceTo(point);
    return distanceStart + (distanceEnd / 2);
  };

  getStreet = (point: LatLng, streets: Street[]): Street | null => {
    if(streets.length === 0){
      return null
    }

    let street = streets[0];
    let currentDistanceFactor = Number.MAX_VALUE;

    streets.forEach(streetSide => {
      const distanceFactor = this.getDistanceFactor(point, streetSide);
      if(distanceFactor < currentDistanceFactor){
        currentDistanceFactor = distanceFactor;
        street = streetSide;
      }
    });

    return street
  };

  routeEnd: {
    [key: string]: LatLngLiteral
  } = {};

  buildRoute = (step: number) => {
    const colors = ['blue', 'yellow', 'green', 'purple'];
    const color = colors[step%this.state.machinesCount];
    const colorEnd = this.routeEnd[color] || {lat: this.state.baseWayPoint.lat, lng: this.state.baseWayPoint.lng};
    const routeStart = colorEnd ? new LatLng(colorEnd.lat, colorEnd.lng) : this.state.baseWayPoint;
    const searchResult = this.getStreet(routeStart, this.props.streetsStore.array.filter(s => !s.clean));
    if(searchResult !== null){
      searchResult.clean = true;
      const routeLine = new Polyline([routeStart], {color: color});
      searchResult.segments.forEach(s => {
        routeLine.addLatLng(s.start);
      });
      routeLine.addLatLng(searchResult.segments[searchResult.segments.length - 1].end);
      this.routeEnd[color] = searchResult.segments[searchResult.segments.length - 1].end;
      routeLine.addTo(this.routesLayer);
    }
    /*const routeLine = new Polyline([this.state.baseWayPoint], {color: 'blue'});
    let searchResult: Street | null = null;
    let streets = this.state.streets;
    let streetCounter = 1;
    do {
      searchResult = this.getStreet(this.state.baseWayPoint, streets);
      if(searchResult !== null){
        streets = streets.filter(s => searchResult && s !== searchResult);
        searchResult.segments.forEach(s => {
          routeLine.addLatLng(s.start);
        });

        const startMarker = new Popup({})
          .setLatLng(searchResult.segments[0].start)
          .setContent('Н ' + streetCounter);
        startMarker.addTo(this.routesLayer);
        const endMarker = new Popup()
          .setLatLng(searchResult.segments[searchResult.segments.length - 1].end)
          .setContent('К ' + streetCounter);

        streetCounter++;
        endMarker.addTo(this.routesLayer);
        routeLine.addLatLng(searchResult.segments[searchResult.segments.length - 1].end);
      }
    } while (searchResult);
    routeLine.addTo(this.routesLayer);*/
  };

  onFormSubmit = (e: FormEvent) => {
    e.preventDefault();
    this.props.streetsStore.array.forEach(s => s.clean = false);
    this.routesLayer.clearLayers();
    this.routeEnd = {};
    for(let i = 0; i < this.props.streetsStore.array.length; i++ ){
      setTimeout(() => {
        this.buildRoute(i);
      }, i*500)
    }
    /*this.props.api.fetchRoutes(this.state.machinesCount).then((routes) => {
      this.setState({ routes }, () => {
        this.state.routes.forEach((r, i) => {
          const routeLine = new Polyline([], {color: colors[i] || 'purple', opacity: .6,});
          routeLine.addTo(this.routesLayer);
          r.forEach((latlng, i) => {
            setTimeout(() => routeLine.addLatLng(new LatLng(latlng[0], latlng[1])), 500 * i)
          });
        })
      })
    });*/
  };

  onChangeMachinesCount = (e: any) => {
    this.setState({machinesCount: e.target.value})
  };

  render(){
    return(
      <div>
        <form onSubmit={this.onFormSubmit}>
          <TextField value={this.state.machinesCount} type="number" onChange={this.onChangeMachinesCount} />
          <Button type="submit">Построить маршруты</Button>
        </form>
        <LeafletMap
          ref={c => this.leafletMap = c}
          height="600px"
          width="100%"
        />
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
)(MapPage) as typeof MapPage;

