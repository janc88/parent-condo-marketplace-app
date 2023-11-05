import com.example.mobdeve_mco.Amenity
import com.example.mobdeve_mco.Listing
import com.example.mobdeve_mco.Property
import com.example.mobdeve_mco.User
import com.example.mobdeve_mco.createDate

object DummyData {
    val imageList: ArrayList<String> = ArrayList()

    init {
        imageList.add("https://static.wixstatic.com/media/a46818_5565205f4b3741fdab3b3f9c4e73db7b~mv2.jpg/v1/fill/w_500,h_472,al_c,lg_1,q_80,enc_auto/a46818_5565205f4b3741fdab3b3f9c4e73db7b~mv2.jpg")
        imageList.add("https://static.wixstatic.com/media/a46818_5565205f4b3741fdab3b3f9c4e73db7b~mv2.jpg/v1/fill/w_500,h_472,al_c,lg_1,q_80,enc_auto/a46818_5565205f4b3741fdab3b3f9c4e73db7b~mv2.jpg")
        imageList.add("https://static.wixstatic.com/media/a46818_5565205f4b3741fdab3b3f9c4e73db7b~mv2.jpg/v1/fill/w_500,h_472,al_c,lg_1,q_80,enc_auto/a46818_5565205f4b3741fdab3b3f9c4e73db7b~mv2.jpg")

    }

    val amenitiesMap = mapOf(
        Amenity.SWIMMING_POOL to false,
        Amenity.GYM to false,
        Amenity.PARKING to false,
        Amenity.WIFI to true,
        Amenity.ELEVATORS to true,
        Amenity.FIRE_ALARM to true,
        Amenity.SECURITY to false,
        Amenity.GENERATOR to true,
        Amenity.CCTV to true,
        Amenity.WATER_TANK to true,
        Amenity.MAILROOM to true
    )

    val description = "Located right beside De La Salle University in Manila, Green Residences Taft gives you a great place to live out the best parts of college life: a stress-free, easy, and fun one that every college student should experience. Packed with unique amenities including a commercial center at the ground floor, function rooms and open areas abound- for those student get-togethers and cram sessions, and to unwind, the game room, gym and pool await."

    val users: List<User> = listOf(
        User(
            "30",
            "Arturo",
            "Caronongan",
            "dlsu.edu.ph",
            createDate(2023, 12, 30),
            "hello!",
            "pfp"),
    )

    val properties: List<Property> = listOf(
        Property(
            "10",
            imageList,
            description,
            "Green Residences",
            -122.4194,
            37.7749,
            "1234 Taft Avenue, Manila, Philippines",
            25000,
            15000,
            24,
            "DLSU",
            swimmingPool = false,
            gym = false,
            parking = true,
            wifi = true,
            elevators = true,
            fireAlarm = true,
            security = true,
            generator = true,
            cctv = true,
            waterTank = true,
            mailRoom = true,
            arrayListOf("22", "23")
        ),
        Property(
            "11",
            imageList,
            description,
            "Archer's Place",
            -122.4194,
            37.7749,
            "678 Leon Guinto St., Manila, Philippines",
            19000,
            15000,
            8,
            "DLSU",
            swimmingPool = true,
            gym = true,
            parking = true,
            wifi = true,
            elevators = true,
            fireAlarm = true,
            security = true,
            generator = true,
            cctv = true,
            waterTank = true,
            mailRoom = true,
            arrayListOf()
        ),
        Property(
            "12",
            imageList,
            description,
            "Green Residences",
            -122.4194,
            37.7749,
            "1234 Taft Avenue, Manila, Philippines",
            25000,
            15000,
            24,
            "DLSU",
            swimmingPool = true,
            gym = true,
            parking = true,
            wifi = true,
            elevators = true,
            fireAlarm = true,
            security = true,
            generator = true,
            cctv = true,
            waterTank = true,
            mailRoom = true,
            arrayListOf("22", "23")
        ),
        Property(
            "13",
            imageList,
            description,
            "Green Residences",
            -122.4194,
            37.7749,
            "1234 Taft Avenue, Manila, Philippines",
            25000,
            15000,
            24,
            "DLSU",
            swimmingPool = true,
            gym = true,
            parking = true,
            wifi = true,
            elevators = true,
            fireAlarm = true,
            security = true,
            generator = true,
            cctv = true,
            waterTank = true,
            mailRoom = true,
            arrayListOf("22", "23")
        )
    )

//    val properties: List<Property> = listOf(
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
//    )

    val listings: List<Listing> = listOf(
        Listing(
            "20",
            imageList,
            "2BR fully furnished",
            15000,
            "Archer's Place",
            "11",
            "DLSU",
            27.23,
            true,
            true,
            1,
            1,
            15,
            false,
            "30",
            "Great location very near DLSU. FUlly furnished",
            false,
        ) ,
        Listing(
            "21",
            imageList,
            "1BR blank",
            17000,
            "Archer's Place",
            "11",
            "DLSU",
            27.23,
            true,
            true,
            1,
            1,
            15,
            false,
            "30",
            "Great location very near DLSU. FUlly furnished",
            true,
        ) ,
        Listing(
            "22",
            imageList,
            "1BR fully furnished",
            25000,
            "Green Residence",
            "10",
            "DLSU",
            27.23,
            true,
            true,
            1,
            1,
            15,
            false,
            "30",
            "Great location very near DLSU. FUlly furnished",
            false,
        ) ,
        Listing(
            "23",
            imageList,
            "1BR fully furnished",
            25000,
            "Green Residence",
            "10",
            "DLSU",
            27.23,
            true,
            true,
            1,
            1,
            15,
            false,
            "30",
            "Great location very near DLSU. FUlly furnished",
            false,
        ) ,
    )

    val propertyList: ArrayList<Property> = ArrayList(properties)
    val listingList: ArrayList<Listing> = ArrayList(listings)
    val userList: ArrayList<User> = ArrayList(users)
}


