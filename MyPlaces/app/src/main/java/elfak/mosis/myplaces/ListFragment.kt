package elfak.mosis.myplaces

import android.os.Bundle
import android.view.*
import android.widget.*
import android.widget.AdapterView.AdapterContextMenuInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import elfak.mosis.myplaces.data.MyPlace
import elfak.mosis.myplaces.databinding.FragmentListBinding
import elfak.mosis.myplaces.model.MyPlacesViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val myPlacesViewModel: MyPlacesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.action_my_places_list)
        item.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_new_place -> {
                this.findNavController().navigate(R.id.action_ListFragment_to_EditFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myPlacesList: ListView = view.findViewById(R.id.my_places_list)
        myPlacesList.adapter =
            ArrayAdapter<MyPlace>(
                view.context,
                android.R.layout.simple_list_item_1,
                myPlacesViewModel.myPlacesList
            )

        myPlacesList.onItemClickListener =
            AdapterView.OnItemClickListener { p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long ->
                var place: MyPlace = p0?.adapter?.getItem(p2) as MyPlace

                myPlacesViewModel.selected = place

                findNavController().navigate(R.id.action_ListFragment_to_ViewFragment)
            }

        myPlacesList.setOnCreateContextMenuListener(object: View.OnCreateContextMenuListener {
            override fun onCreateContextMenu(
                menu: ContextMenu?,
                view: View?,
                menuInfo: ContextMenu.ContextMenuInfo?
            ) {
                val info = menuInfo as AdapterContextMenuInfo
                val myPlace = myPlacesViewModel.myPlacesList[menuInfo.position]
                menu?.add(0, 1, 1, "1")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}