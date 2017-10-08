package com.df.dianping;

import android.location.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PoiResultData {
    // get the information of Restaurants
	public static ArrayList<Map<String, Object>> getRestaurantData()
	{
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "Maha");
		map.put("price", 92);
		map.put("addr", "21 Bond St,Melbourne,Victoria 3000");
		map.put("latitude",-37.818225);
        map.put("longitude",144.96245);
        map.put("phone","03 9629 5900");
        map.put("self-description","Mamak is an award winning Malaysian restaurant, " +
                "with branches in Sydney and Melbourne. Named after the roadside stalls of Kuala Lumpur that peddle Indian Malay street ");
		map.put("recommended","Roast pumpkin, pickled beetroot, roast pumpkin");
        map.put("busy", 2);
		map.put("star", 45);
        map.put("taste_score",3.5);
        map.put("environment_score",3.0);
        map.put("service_score",2.8);
        map.put("distance", "5.9Km");
		list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "The French Brasserie");
        map.put("price", 85);
        map.put("addr", "2 Malthouse Lane, Melbourne,Victoria 3000");
        map.put("latitude",-37.81542);
        map.put("longitude",144.97182);
        map.put("phone","03 9662 1632");
        map.put("self-description","The French Brasserie is the perfect place for your next soirée." +
                " Be it a corporate event, wedding, birthday, engagement, product launch, or a part for no particular reason at all!");
        map.put("recommended","Charcuterie ,grilled eye filet,carrots, Roast duck breast with slow cook egg");
        map.put("busy", 2);
        map.put("star", 40);
        map.put("taste_score",3.5);
        map.put("environment_score",4.0);
        map.put("service_score",4.5);
        map.put("distance", "6.1Km");
        list.add(map);

		map = new HashMap<String, Object>();
		map.put("name", "Coda");
		map.put("price", 6285);
		map.put("addr", "141 Flinders Lane,Melbourne,Victoria 3000");
        map.put("latitude",-37.81542);
        map.put("longitude",144.97182);
        map.put("phone","03 9650 3155");

        map.put("distance", "3.5Km");
        map.put("self-description","Coda’s relaxed environment provides a versatile setting for a range of engagements whatever your occasion. " +
                "From a quick pre-football meal to a long Sunday lunch, business dining or a cosy seat for an anniversary, we look forward to your visit.");
        map.put("recommended","Blistered green beans with pork and XO sauce,Roasted yellow duck curry");
        map.put("busy", 1);
        map.put("star", 45);
        map.put("taste_score",4.0);
        map.put("environment_score",5.0);
        map.put("service_score",4.5);
		list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "Laksa King");
        map.put("price", 36);
        map.put("addr", "6-12 Pin Oak Cres, Melbourne, Victoria 3031");
        map.put("latitude",-37.78788);
        map.put("longitude",144.92955);
        map.put("phone","03 9372 6383");

        map.put("self-description","Urbanspoon Melbourne’s Most Popular Restaurant" +
                "We always try to maintain our high quality food and service. Thank you for all your support!");
        map.put("recommended","Curry fish head,Sea perch with black truffle,Smoked duck breast ");
        map.put("busy", 1);
        map.put("star", 35);
        map.put("taste_score",3.5);
        map.put("environment_score",3.2);
        map.put("service_score",3.8);
        map.put("distance", "4.2Km");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "Betty’s Burgers & Concrete Co");
        map.put("price", 13);
        map.put("addr", "97 Elizabeth St, Melbourne, Victoria 3000");
        map.put("latitude",-37.81590);
        map.put("longitude",144.96353);
        map.put("phone","03 9642 5823");

        map.put("self-description","The Betty’s Burgers & Concrete Co. concept is a classic burger shack, serving the freshest, juiciest and mouth-watering burgers around. As with any great burger," +
                "it needs a supporting cast and in this case, is our house-made frozen custards or concretes. ");
        map.put("recommended","Crispy chicken ,Betty’s Classic and a Shroom Burger combined");
        map.put("busy", 0);
        map.put("star", 35);
        map.put("taste_score",3.5);
        map.put("environment_score",3.3);
        map.put("service_score",3.7);
        map.put("distance", "350m");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "Scopri");
        map.put("price", 48);
        map.put("addr", "191 Nicholson St | Carlton, Melbourne, Victoria 3053");
        map.put("latitude",-37.79569);
        map.put("longitude",144.97499);
        map.put("phone","03 9347 8252");

        map.put("self-description","Scopri, which means to discover, or to find out, in Italian, comes directly from the experience gained in Italy by owners." +
                "They regularly travel through Italy, eating and drinking their way through its 20 regions, " +
                "‘discovering’ along the way the diversity of Italian food and wine");
        map.put("recommended","2010 Olivier Horiot '5 Sens' Champagne ,Romanesco zucchini and prawn risotto");
        map.put("busy", 1);
        map.put("star", 30);
        map.put("taste_score",3.0);
        map.put("environment_score",3.2);
        map.put("service_score",2.8);
        map.put("distance", "5.8Km");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "Metro Burger");
        map.put("price", 17);
        map.put("addr", "12 Degraves St, Melbourne, Victoria 3000");
        map.put("latitude",-37.81753);
        map.put("longitude",144.96600);
        map.put("phone","03 9671 4069");

        map.put("self-description","If you are looking for one of the best burgers in Melbourne then head down to Metro Burgers on Degraves Street (just off Flinders Street near Flinders Station)." +
                " This tiny little burger joint serves up some seriously delicious burgers!");
        map.put("recommended","Ecpress Breakfast,Smashed Avocado,Cranberry Granola");
        map.put("busy", 1);
        map.put("star", 20);
        map.put("taste_score",2.0);
        map.put("environment_score",1.2);
        map.put("service_score",2.8);
        map.put("distance", "5.8Km");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "Gru Thai Restaurant");
        map.put("price", 26);
        map.put("addr", "331 Clarendon Street, Melbourne,Victoria");
        map.put("latitude",-37.7879);
        map.put("longitude",144.9296);
        map.put("phone","03 9699 4250");

        map.put("self-description","Down-to-earth, warm and busy restaurant with leather chairs and hanging lamps, for Thai standards.");
        map.put("recommended","Khao Pad Sapparod,Gang Som Cha-om Gung,Fish in the Garden ");
        map.put("busy", 1);
        map.put("star", 45);
        map.put("taste_score",5.0);
        map.put("environment_score",3.9);
        map.put("service_score",4.1);
        map.put("distance", "5.8Km");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "Nando's");
        map.put("price", 18);
        map.put("addr", "551 Flinders St|Central Tower,Melbourne,Victoria 3000");
        map.put("latitude",-37.82096);
        map.put("longitude",144.95647);
        map.put("phone","03 9620 7279");

        map.put("self-description","Nando's is an international casual dining restaurant chain originating in South Africa. Founded in 1987, Nando's operates about 1,000 outlets in 30 countries." +
                " Nando's specialises in grilled chicken dishes with various peri-peri marinades.");
        map.put("recommended","Churrasco BBQ Ribs, Grilled Tenderloins,Chick 'n' Hummus Pita");
        map.put("busy", 2);
        map.put("star",20);
        map.put("taste_score",1.9);
        map.put("environment_score",1.5);
        map.put("service_score",2.6);
        map.put("distance", "5.8Km");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "Udon Yasan");
        map.put("price", 28);
        map.put("addr", "186 Bourke St,Melbourne VIC 3000,Melbourne,Victoria 3000");
        map.put("latitude",-37.81266);
        map.put("longitude",144.96768);
        map.put("phone","03 9620 7279");

        map.put("self-description","Australian flour 100% and 24 hours aged home made noodle;" +
                "made with Kyokane Jyo-zo co,ltd, established in 1953 in Kagawa,Japan");
        map.put("recommended","Noodle with dipping sauce, Beef noodle soup, Kimchi noodle soup ,Egg noodle soup");
        map.put("busy", 0);
        map.put("star", 45);
        map.put("taste_score",4.5);
        map.put("environment_score",4.1);
        map.put("service_score",4.6);
        map.put("distance", "5.8Km");
        list.add(map);

		return list;
	}
    // calculate the distance between current and restaurant then put it into map
    public static ArrayList<Map<String,Object>> calculateDistance(ArrayList<Map<String, Object>> mDataList, Location location) {
        System.out.println("transfer location AAAA");
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < mDataList.size(); i++) {
            Map<String,Object>map = mDataList.get(i);
            String strDistance;
            double latitude =((Double) map.get("latitude")).doubleValue();
            double longitude =((Double) map.get("longitude")).doubleValue();
            Location newLocation = new Location("restaurant");
            newLocation.setLatitude(latitude);
            newLocation.setLongitude(longitude);
            double distance = location.distanceTo(newLocation);
            if(distance<1000){
                distance =(int) distance;
                strDistance =""+distance+"m";
            }else{
                distance = ((int)(distance/100))/10.0;
                strDistance =""+distance+"Km";
            }
            map.put("distance",strDistance);
            list.add(map);
        }
        return list;
    }
}
