import { LatLngLiteral } from "leaflet";
import { Model } from "../common/Resource";

export interface StreetSegment {
  start: LatLngLiteral
  end: LatLngLiteral
  width: number
  twoWay?: number
  priority?: string
}

export interface Street extends Model{
  id?: number
  name: string
  priority?: string
  segments: StreetSegment[]
  clean: boolean;
}

export type Route = number[][]
