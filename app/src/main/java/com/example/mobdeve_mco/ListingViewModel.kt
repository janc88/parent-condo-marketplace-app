import androidx.lifecycle.ViewModel
import com.example.mobdeve_mco.Listing

class ListingViewModel : ViewModel() {
    var listing: Listing = Listing(
        id = -1,
        imageList = ArrayList(),
        title = "",
        price = -1,
        property = "",
        propertyId = -1,
        university = "",
        area = -1.0,
        isFurnished = false,
        isStudioType = false,
        numBedroom = -1,
        numBathroom = -1,
        floor = -1,
        balcony = false,
        ownerId = "-1",
        description = "",
        isRented = false
    )
}
