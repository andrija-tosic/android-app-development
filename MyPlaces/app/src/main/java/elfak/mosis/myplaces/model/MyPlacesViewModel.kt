package elfak.mosis.myplaces.model

import androidx.lifecycle.ViewModel
import elfak.mosis.myplaces.data.MyPlace

class MyPlacesViewModel : ViewModel() {
    var myPlacesList: ArrayList<MyPlace> = arrayListOf(MyPlace("Place test 1", "Desc1"))

    fun addPlace(place: MyPlace) {
        this.myPlacesList.add(place)
    }
}