package com.google.netpcapanalysis.dao;

import com.google.netpcapanalysis.interfaces.dao.GeolocationDao;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Country;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.*;

public class GeolocationDaoImpl implements GeolocationDao {

  private static final String GEO_DB_LOCATION = "GeoLite2-City.mmdb";
  private File database;
  private DatabaseReader reader;

  public GeolocationDaoImpl() {
    try {
      if (GeolocationDaoImpl.class == null) throw new Error("class null");
      if (GeolocationDaoImpl.class.getClassLoader() == null) throw new Error("no class loader");
      if (GeolocationDaoImpl.class.getClassLoader().getResource("resources/" + GEO_DB_LOCATION) == null) throw new Error("resource null");
      URL geoDBUrl = GeolocationDaoImpl.class.getClassLoader().getResource("resources/" + GEO_DB_LOCATION);
      database = new File(geoDBUrl.toURI());
      reader = new DatabaseReader.Builder(database).build();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }
  }
  
  public String getCountry(InetAddress ip) {
    try {
      InetAddress ipAddress = InetAddress.getByName(ip.getHostAddress());
      CityResponse response = reader.city(ipAddress);

      Country country = response.getCountry();
      return country.getName();
    } catch (Exception e) {
      return "unknown";
    }
  }
}
