export function replaceMapsWithArrays(object: any){
  for(let key in object){
    if (object.hasOwnProperty(key)){
      const value = object[key];
      if (value instanceof Map){
        object[key] = Array.from(value);
      }
      if (value instanceof Object){
        replaceMapsWithArrays(value)
      }
    }
  } 
}

export function restoreMapsFromArrays(object: any){
  for(let key in object){
    if (object.hasOwnProperty(key)){
      const value = object[key];
      if (key.toLowerCase().indexOf('map') !== -1 && Array.isArray(value) && value.every(_ => Array.isArray(_) && _.length === 2) ){
        try{
          object[key] = new Map(value);  
        } catch (e){
        }
      }
      if (value instanceof Object){
        restoreMapsFromArrays(value)
      }
    }
  }
}