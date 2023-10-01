import com.example.mobdeve_mco.Amenity
import com.example.mobdeve_mco.Listing
import com.example.mobdeve_mco.Property
import com.example.mobdeve_mco.R

object DummyData {
    val imageList: ArrayList<Int> = ArrayList()

    init {
        imageList.add(R.drawable.bed)
        imageList.add(R.drawable.bed)
        imageList.add(R.drawable.bed)
        imageList.add(R.drawable.bed)
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

    val properties: List<Property> = listOf(
        Property(10, imageList, description, "Green Residences", -122.4194, 37.7749, "1234 Taft Avenue", 25000, 15000, 24, "DLSU", amenitiesMap, arrayListOf(20, 21, 22, 23)),
        Property(11, imageList, description, "Green Residences", -122.4194, 37.7749, "1234 Taft Avenue", 25000, 15000, 24, "DLSU", amenitiesMap, arrayListOf(20, 21, 22, 23)),
        Property(12, imageList, description, "Green Residences", -122.4194, 37.7749, "1234 Taft Avenue", 25000, 15000, 24, "DLSU", amenitiesMap, arrayListOf(20, 21, 22, 23)),
        Property(13, imageList, description, "Green Residences", -122.4194, 37.7749, "1234 Taft Avenue", 25000, 15000, 24, "DLSU", amenitiesMap, arrayListOf(20, 21, 22, 23))
    )

    val listings: List<Listing> = listOf(
        Listing(20, imageList, "1BR fully furnished", 25000, "Green Residence", 10, "DLSU") ,
        Listing(21, imageList, "1BR fully furnished", 25000, "Green Residence", 10, "DLSU"),
        Listing(22, imageList, "1BR fully furnished", 25000, "Green Residence", 10, "DLSU"),
        Listing(23, imageList, "1BR fully furnished", 25000, "Green Residence", 10, "DLSU"),
    )

    val propertyList: ArrayList<Property> = ArrayList(properties)
    val listingList: ArrayList<Listing> = ArrayList(listings)
}


