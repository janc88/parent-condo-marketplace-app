package com.example.mobdeve_mco

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.squareup.picasso.Picasso


class ListingActivity : AppCompatActivity() {

    private lateinit var tvListingTitle : TextView
    private lateinit var imageSlider : ImageSlider

    private lateinit var tvPropertyNameTop : TextView
    private lateinit var tvSqm : TextView
    private lateinit var tvFurnished : TextView
    private lateinit var tvBedroom : TextView
    private lateinit var tvFloor : TextView
    private lateinit var tvBathroom : TextView
    private lateinit var tvBalcony : TextView
    private lateinit var tvDescription : TextView

    private lateinit var tvOwner : TextView
    private lateinit var tvDateJoined : TextView
    private lateinit var tvPropertyNameBottom : TextView
    private lateinit var tvAddress : TextView
    private lateinit var tvPrice : TextView
    private lateinit var ivUserPfp : ImageView
    private lateinit var ivPropertyImg : ImageView

    private lateinit var btnCall : CardView
    private lateinit var btnMessage : CardView
    private lateinit var btnContact : Button


    private lateinit var tvStudioType : TextView

    private lateinit var btnBack: CardView
    private lateinit var cvHeart: CardView
    private lateinit var btnHeart: ImageView

    private lateinit var rvSimilarListings: RecyclerView
    private lateinit var glAmenities: GridLayout

    private lateinit var listing: Listing

    private lateinit var featuredListingAdapter: FeaturedListingAdapter
    private var isLiked = false

    private lateinit var likesHelper: LikesHelper
    private val firebaseHelper = FirebaseHelper.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing)

        listing = intent.getParcelableExtra<Listing>("listing")!!

        bindViews()
        init()

        val window = this.window

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

    }

    private fun bindViews(){
        tvListingTitle = findViewById(R.id.tvListingTitle)
        imageSlider = findViewById(R.id.imageSlider)
        tvPropertyNameTop = findViewById(R.id.tvPropertyNameTop)
        tvSqm = findViewById(R.id.tvSqm)
        tvFurnished = findViewById(R.id.tvFurnished)
        tvBedroom = findViewById(R.id.tvBedroom)
        tvFloor = findViewById(R.id.tvFloor)
        tvBathroom = findViewById(R.id.tvBathroom)
        tvBalcony = findViewById(R.id.tvBalcony)
        tvOwner = findViewById(R.id.tvOwner)
        tvDateJoined = findViewById(R.id.tvDateJoined)
        tvPropertyNameBottom = findViewById(R.id.tvPropertyNameBottom)
        tvAddress = findViewById(R.id.tvAddress)
        rvSimilarListings = findViewById(R.id.rvSimilarListings)
        tvStudioType = findViewById(R.id.tvStudioType)
        tvDescription = findViewById(R.id.tvDescription)
        glAmenities = findViewById(R.id.glAmenities)
        tvPrice = findViewById(R.id.tvPrice)
        btnBack = findViewById(R.id.btnBack)
        cvHeart = findViewById(R.id.cvHeart)
        btnHeart = findViewById(R.id.btnHeart)
        ivUserPfp = findViewById(R.id.ivUserPfp)
        ivPropertyImg = findViewById(R.id.ivPropertyImg)
        btnContact = findViewById(R.id.btnContact)
        btnMessage = findViewById(R.id.btnMessage)
        btnCall = findViewById(R.id.btnCall)
    }

    private fun init(){
        likesHelper = LikesHelper()

        likesHelper.isListingLiked(listing.id) { isLiked ->
            if (!isLiked) {
                btnHeart.setImageResource(R.drawable.ic_heart)
                Log.d("likes", "liked")
            } else {
                btnHeart.setImageResource(R.drawable.ic_heart_liked)
            }
        }

        firebaseHelper.getPropertyFromFirestore(listing.propertyId) { property ->
            if (property != null) {
                tvPropertyNameTop.text = property.name
                tvPropertyNameBottom.text = property.name
                tvAddress.text = property.address
                addAmenitiesToGridLayout(this, glAmenities, property)
                Picasso.get().load(property.imageList[0]).into(ivPropertyImg)

                ivPropertyImg.setOnClickListener{
                    val intent = Intent(this, PropertyActivity::class.java)
                    intent.putExtra("property", property)
                    startActivity(intent)
                }
            }
        }

        btnBack.setOnClickListener{
            onBackPressed()
        }

        cvHeart.setOnClickListener{
            likesHelper.handleLikeButtonClick(listing.id){
                if (isLiked) {
                    btnHeart.setImageResource(R.drawable.ic_heart)
                } else {
                    btnHeart.setImageResource(R.drawable.ic_heart_liked)
                }
                isLiked = !isLiked
            }
        }

        tvListingTitle.text = listing.title

        val imageList = ArrayList<SlideModel>()
        for(image in listing.imageList){
            imageList.add(SlideModel(image, ScaleTypes.CENTER_CROP))
        }
        imageSlider.setImageList(imageList)

        tvListingTitle.text = listing.title
        tvStudioType.isVisible = listing.studioType
        tvSqm.text = "${listing.area} sqm"

        if(listing.furnished){
            tvFurnished.text = "Furnished"
        }else{
            tvFurnished.text = "Unfurnished"
        }

        if(listing.numBedroom > 1){
            tvBedroom.text = "${listing.numBedroom} Bedrooms"
        }else{
            tvBedroom.text = "1 Bedroom"
        }

        tvFloor.text = formatFloor(listing.floor)

        if(listing.numBathroom > 1){
            tvBathroom.text = "${listing.numBathroom} Bathrooms"
        }else{
            tvBathroom.text = "1 Bathroom"
        }

        if(listing.balcony){
            tvBalcony.text = "Balcony"
        }else{
            tvBalcony.text = "No Balcony"
        }

        tvOwner.text = "Temp owner"
        tvDateJoined.text = "Joined Temp 2022"
        tvDescription.text = listing.description

        firebaseHelper.getUserFromFirestore(listing.ownerId) { user ->
            if (user != null) {
                tvOwner.text = "${user.firstname} ${user.lastname}"
                tvDateJoined.text = "Joined in ${formatDateJoined(user.dateAccountCreated)}"
                Picasso.get().load(user.pfp).into(ivUserPfp)


                btnCall.setOnClickListener{
                    if(user.contactNum != ""){
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:" + user.contactNum)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "This user has not provided a contact number.", Toast.LENGTH_SHORT).show()
                    }

                }

                btnMessage.setOnClickListener{
//                    val smsIntent = Intent(Intent.ACTION_VIEW)
//                    smsIntent.type = "vnd.android-dir/mms-sms"
//                    smsIntent.putExtra("address", user.contactNum)
//                    smsIntent.putExtra("sms_body", "Hi, I'm interested to know more about your unit in Parent.")
//                    startActivity(smsIntent)
                    if(user.contactNum != "") {
                        val recipientNumber = user.contactNum
                        val messageBody = "Hi, I'm interested to know more about your unit in Parent."

                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse("smsto:$recipientNumber")
                        intent.putExtra("sms_body", messageBody)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "This user has not provided a contact number.", Toast.LENGTH_SHORT).show()
                    }
                }

                btnContact.setOnClickListener{
                    val email = user.email
                    val contactNum = user.contactNum
                    val bottomSheetFragment = ContactBottomSheetFragment.newInstance(email, contactNum)
                    bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
                }
            }
        }

        tvPrice.text = listing.price.formatPrice()

        setupRecyclerView()

    }




    private fun setupRecyclerView(){
        rvSimilarListings.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvSimilarListings.layoutManager = layoutManager


        firebaseHelper.getSimilarAvailableListings(listing) { similarListings ->
            if (similarListings != null) {
                featuredListingAdapter = FeaturedListingAdapter(similarListings as ArrayList<Listing>)
                rvSimilarListings.adapter = featuredListingAdapter

                featuredListingAdapter.onItemClick = {
                    val intent = Intent(this, ListingActivity::class.java)
                    intent.putExtra("listing", it)
                    startActivity(intent)
                }
            }
        }

    }

    fun addAmenitiesToGridLayout(context: Context, gridLayout: GridLayout, property: Property) {
        val maxAmenities = 6

        val amenities = listOf(
            Pair("Swimming Pool", property.swimmingPool),
            Pair("Gym", property.gym),
            Pair("Parking", property.parking),
            Pair("Wi-Fi", property.wifi),
            Pair("Elevators", property.elevators),
            Pair("Fire Alarm", property.fireAlarm),
            Pair("Security", property.security),
            Pair("Generator", property.generator),
            Pair("CCTV", property.cctv),
            Pair("Water Tank", property.waterTank),
            Pair("Mailroom", property.mailRoom)
        )

        var addedAmenities = 0

        for ((amenityText, isAvailable) in amenities) {
            if (!isAvailable) {
                continue
            }

            val itemLayout = LinearLayout(context)
            itemLayout.orientation = LinearLayout.HORIZONTAL

            val imageView = ImageView(context)
            imageView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            imageView.setImageResource(R.drawable.ic_check)

            val textView = TextView(context)
            textView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textView.text = amenityText
            textView.textSize = 12f

            itemLayout.addView(imageView)
            itemLayout.addView(textView)

            val params = GridLayout.LayoutParams()
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            itemLayout.layoutParams = params

            gridLayout.addView(itemLayout)

            addedAmenities++
            if (addedAmenities >= maxAmenities) {
                break
            }
        }
    }



}