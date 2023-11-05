import com.example.mobdeve_mco.Amenity
import com.example.mobdeve_mco.Listing
import com.example.mobdeve_mco.Property
import com.example.mobdeve_mco.R
import com.example.mobdeve_mco.User
import com.example.mobdeve_mco.createDate
import java.time.LocalDate
import java.util.Date

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
            10,
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
            amenitiesMap,
            arrayListOf(22, 23)
        ),
        Property(
            11,
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
            amenitiesMap,
            arrayListOf()
        ),
        Property(
            12,
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
            amenitiesMap,
            arrayListOf(22, 23)
        ),
        Property(
            13,
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
            amenitiesMap,
            arrayListOf(22, 23)
        )
    )

    val listings: List<Listing> = listOf(
        Listing(
            20,
            imageList,
            "2BR fully furnished",
            15000,
            "Archer's Place",
            11,
            "DLSU",
            27.23,
            true,
            true,
            1,
            1,
            15,
            false,
            30,
            "Great location very near DLSU. FUlly furnished",
            false,
        ) ,
        Listing(
            21,
            imageList,
            "1BR blank",
            17000,
            "Archer's Place",
            11,
            "DLSU",
            27.23,
            true,
            true,
            1,
            1,
            15,
            false,
            30,
            "Great location very near DLSU. FUlly furnished",
            true,
        ) ,
        Listing(
            22,
            imageList,
            "1BR fully furnished",
            25000,
            "Green Residence",
            10,
            "DLSU",
            27.23,
            true,
            true,
            1,
            1,
            15,
            false,
            30,
            "Great location very near DLSU. FUlly furnished",
            false,
        ) ,
        Listing(
            23,
            imageList,
            "1BR fully furnished",
            25000,
            "Green Residence",
            10,
            "DLSU",
            27.23,
            true,
            true,
            1,
            1,
            15,
            false,
            30,
            "Great location very near DLSU. FUlly furnished",
            false,
        ) ,
    )

    val propertyList: ArrayList<Property> = ArrayList(properties)
    val listingList: ArrayList<Listing> = ArrayList(listings)
    val userList: ArrayList<User> = ArrayList(users)
}


