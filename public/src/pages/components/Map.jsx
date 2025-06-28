import React, { useRef, useEffect, useState } from "react";
import maplibregl from "maplibre-gl";
import "maplibre-gl/dist/maplibre-gl.css";
import configData from "../../config";
import Box from "@mui/material/Box";

export default function Map() {
  const mapContainer = useRef(null);
  const map = useRef(null);

  const center = { lng: 27.5590, lat: 53.9006 };
  const [zoom] = useState(12);

  useEffect(() => {
    if (map.current) return;

    map.current = new maplibregl.Map({
      container: mapContainer.current,
      style: `https://api.maptiler.com/maps/streets/style.json?key=${configData.MAPTILER_API_KEY}`,
      center: [center.lng, center.lat],
      zoom: zoom
    });

    const buildings = [
      {
        lng: 27.6459,
        lat: 53.9315,
        label: "Национальная библиотека Беларуси"
      },
      {
        lng: 27.5583,
        lat: 53.8986,
        label: "Минский лингвистический университет"
      },
      {
        lng: 27.5474,
        lat: 53.9089,
        label: "ТЦ Galleria Minsk"
      }
    ];

    buildings.forEach(({ lng, lat, label }) => {
      new maplibregl.Marker({ color: "#FF0000" })
        .setLngLat([lng, lat])
        .setPopup(new maplibregl.Popup().setText(label))
        .addTo(map.current);
    });
  }, [center.lng, center.lat, zoom]);

  return (
    <Box sx={{ display: "flex" }}>
      <div className="container">
        <div ref={mapContainer} id="map" className="map" />
      </div>
    </Box>
  );
}
