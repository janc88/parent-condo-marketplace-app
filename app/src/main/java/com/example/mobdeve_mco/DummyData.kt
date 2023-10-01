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

    val properties: List<Property> = listOf(
        Property(10, imageList, "Green Residences", 25000, 15000, 24, "DLSU", amenitiesMap, arrayListOf(20, 21, 22, 23)),
        Property(11, imageList, "Green Residences", 25000, 15000, 24, "DLSU", amenitiesMap, arrayListOf(20, 21, 22, 23)),
        Property(12, imageList, "Green Residences", 25000, 15000, 24, "DLSU", amenitiesMap, arrayListOf(20, 21, 22, 23)),
        Property(13, imageList, "Green Residences", 25000, 15000, 24, "DLSU", amenitiesMap, arrayListOf(20, 21, 22, 23))
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


