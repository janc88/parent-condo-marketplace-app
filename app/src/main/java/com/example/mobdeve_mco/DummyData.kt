import com.example.mobdeve_mco.Amenity
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
        Property(imageList, "Green Residences", 25000, 15000, 24, "DLSU", amenitiesMap),
        Property(imageList, "Green Residences", 25000, 15000, 24, "DLSU", amenitiesMap),
        Property(imageList, "Green Residences", 25000, 15000, 24, "DLSU", amenitiesMap),
        Property(imageList, "Green Residences", 25000, 15000, 24, "DLSU", amenitiesMap)
    )

    val propertyList: ArrayList<Property> = ArrayList(properties)
}


