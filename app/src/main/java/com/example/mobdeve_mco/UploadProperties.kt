package com.example.mobdeve_mco

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

object PropertyUtils {
    val db = Firebase.firestore

    // this function is not meant to be run by users. it is only for the admin as users cannot add properties
    fun addPropertiesToFirestore(properties: List<Property>) {
        val propertiesCollection = db.collection("properties")

        for (property in properties) {
            propertiesCollection.add(property)
                .addOnSuccessListener { documentReference ->
                    Log.d("test", "Properties have been added to firestore successfully")
                }
                .addOnFailureListener { e ->
                    Log.d("test", "Properties failed to be added to firestore")
                }
        }
    }


    val properties = listOf(
//        Property(
//            id = 0,
//            imageList = ArrayList(
//                listOf(
//                    "https://www.homeassist.ph/propertyimages/158/hero/Green%20Residences%20Overview.jpg",
//                    "https://static.wixstatic.com/media/a46818_e20f11266a2b4851b459eb7b50f63474~mv2.jpg/v1/fill/w_640,h_400,al_c,q_80,usm_0.66_1.00_0.01,enc_auto/a46818_e20f11266a2b4851b459eb7b50f63474~mv2.jpg",
//                    "https://www.homeassist.ph/propertyimages/158/slideshow/Green%20Residences%20-%20Fitness%20Gym,%20Artist's%20Perspective.png",
//                    "https://smdccondoproperty.com/wp-content/uploads/2016/04/green-residences-lobby-september-20152.jpg"
//                )
//            ),
//            description = "Love is all about giving. And as parents, we show love for our children by giving them the best. Because why do we work tirelessly each day if not for them? Why do we think about the future if it were not for their own good? If you are a parent who has a child that’s about to start college in Manila or is already in college, give them more love and help them become a success in their own right by investing in Green Residences along Taft Avenue, Malate. Right beside De La Salle University, Green Residences gives you the assurance that your child will not be subject to the burdens of commuting in the city. Give them that gift and they will certainly flourish.",
//            name = "Green Residences",
//            longitude = 120.99288496137966,
//            latitude = 14.568126863236335,
//            address = "2441, 1004 Taft Ave, Malate, Manila, 1004 Metro Manila",
//            highestPrice = 0,
//            lowestPrice = 0,
//            numListings = 0,
//            university = "DLSU",
//            SWIMMING_POOL = true,
//            GYM = true,
//            PARKING = true,
//            WIFI = true,
//            ELEVATORS = true,
//            FIRE_ALARM = true,
//            SECURITY = true,
//            GENERATOR = true,
//            CCTV = true,
//            WATER_TANK = true,
//            MAILROOM = true,
//            listingIds = ArrayList()
//        ),
//        Property(
//            id = 0,
//            imageList = ArrayList(
//                listOf(
//                    "https://eton.com.ph/wp-content/uploads/2020/02/OAP1-1030x853.jpg",
//                    "https://static-mp.lamudi.com/static/media/bm9uZS9ub25l/1000x620/3d65d64aacbf73.jpg",
//                    "https://manilaphilrealestate.weebly.com/uploads/6/0/1/4/6014554/6723267_orig.jpg"
//                )
//            ),
//            description = "Located close to Manila’s top education institutions, One Archers' Place along Taft Avenue is designed for students and young professionals. The 31-storey twin tower residential condominium offers studio and one-bedroom flats with lifestyle amenities, and two floors of retail and dining choices. Located near the LRT and within easy reach of the Makati business district, One Archers Place offers a prime investment opportunity with its strong leasing potential brought about by year-round tenant demand.",
//            name = "One Archers’ Place",
//            longitude = 120.99254043862305,
//            latitude = 14.567458479889199,
//            address = "2311 Taft Ave, Malate, Manila, 1004 Metro Manila",
//            highestPrice = 0,
//            lowestPrice = 0,
//            numListings = 0,
//            university = "DLSU",
//            SWIMMING_POOL = true,
//            GYM = true,
//            PARKING = true,
//            WIFI = true,
//            ELEVATORS = true,
//            FIRE_ALARM = true,
//            SECURITY = true,
//            GENERATOR = true,
//            CCTV = true,
//            WATER_TANK = true,
//            MAILROOM = true,
//            listingIds = ArrayList()
//        ),

        Property(
            id = "0",
            imageList = ArrayList(
                listOf(
                    "https://www.vistaresidences.com.ph/assets/blog-images/condo-living-_--vista-taft-min.png",
                    "https://propertyreport.ph/wp-content/uploads/2019/03/V11.jpg",
                    "https://pix10.agoda.net/hotelImages/578/5780906/5780906_18090621100067870200.jpg?ca=0&ce=1&s=414x232",
                )
            ),
            description = "Take your condo living to the next level as Vista Taft by Vista Residences gives you the right combination of modernity and convenience. With its contemporary design, modern-day amenities, easy access, and walkability to various establishments, services, and universities, Vista Taft by Vista Residences speaks of what's young and innovative and brings about a taste of good life in the middle of downtown Manila. Vista Taft offers Studio and 1 Bedroom units ideal for students, young professionals, and starting families",
            name = "Vista Taft Residences",
            longitude = 120.994725622398,
            latitude = 14.563108530999047,
            address = "2587 Taft Ave, Malate, Manila",
            highestPrice = 0,
            lowestPrice = 0,
            numListings = 0,
            university = "DLSU",
            SWIMMING_POOL = true,
            GYM = true,
            PARKING = true,
            WIFI = true,
            ELEVATORS = true,
            FIRE_ALARM = true,
            SECURITY = true,
            GENERATOR = true,
            CCTV = true,
            WATER_TANK = true,
            MAILROOM = true,
            listingIds = ArrayList()
        ),
        Property(
            id = "0",
            imageList = ArrayList(
                listOf(
                    "https://www.santeco-realty.com/uploads/5/7/7/2/5772592/879440.jpg?382",
                    "https://pinoydeal.ph/oc-content/uploads/194/21979_original.jpg",
                    "https://media.karousell.com/media/photos/products/2023/10/2/r_square_residences_near_dlsu__1696231114_3a1fc6f7_progressive.jpg"
                )
            ),
            description = "R Square Residences is a 50-storey high-end residential condominium in Manila. Towering above the rest, it offers larger and luxurious units with areas ranging from 20 sqm to 115 sqm. Having a 3-level Commercial Mall & 1 whole floor amenity area offers R Square Residences’ unit owners a one stop shop where everything they need is within their reach. A wide array of retail experience and recreational activities makes it more unique amongst other developments.\n" +
                    "\n" +
                    "With the aim to serve an increasing academic population, R Square Residences has chic and spacious studio units which can house up to 6 students. With its high-grade finishes, leasing a condominium has never been this cosy and secure. R Square Residences unit owners will get to witness the stunning sun set and sun rise on the east side and enjoy the extensive view of CBD on the west side. Residents here will savour a life full of convenience, security and elegance as the project is defined by its generous features and the best value proposition.",
            name = "R Square Residences",
            longitude = 120.99541876089093,
            latitude = 14.56265449989046,
            address = "Taft Avenue, corner P Ocampo St, Malate, Manila",
            highestPrice = 0,
            lowestPrice = 0,
            numListings = 0,
            university = "DLSU",
            SWIMMING_POOL = true,
            GYM = true,
            PARKING = true,
            WIFI = true,
            ELEVATORS = true,
            FIRE_ALARM = true,
            SECURITY = true,
            GENERATOR = true,
            CCTV = true,
            WATER_TANK = true,
            MAILROOM = true,
            listingIds = ArrayList()
        ),
    )
}

