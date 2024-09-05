package zapcom.venkat.assignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import factory.ResponseViewModelFactory
import repository.JSONRepositoryImpl
import viewmodel.JSONViewModel


class MainFragment : Fragment() {

    lateinit var horizontalScrollView: RecyclerView
    lateinit var banner: LinearLayout
    lateinit var splitBanner: LinearLayout

    private var mAccountViewModel: JSONViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_main, container, false)
        mAccountViewModel = ResponseViewModelFactory(JSONRepositoryImpl(App.getApi())).create()
        initUIComponents(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val jsonResp: MutableLiveData<JsonArray>? = mAccountViewModel?.fetchJSONHistoryList()
//        jsonResp?.observe(viewLifecycleOwner, Observer {
//            val jsonArrayResp: JsonArray = it
//            //TODO: Process this jsonArrayResp
//            println(jsonArrayResp)
//        })

        mAccountViewModel?.fetchJSONHistoryList()

        with(mAccountViewModel) {
            handleObserverForLoading(view, this)
            handleObserverForNoHistory(this)
            handleObserverForHistoryList(this)
            handleObserverForError(view, this)
        }


    }

    fun initUIComponents(view: View){
        horizontalScrollView = view.findViewById(R.id.horizontalFreeScroll)
        banner = view.findViewById(R.id.banner)
        splitBanner = view.findViewById(R.id.splitBanner)
    }

    private fun handleObserverForLoading(
        view: View?,
        jsonHistoryViewModel: JSONViewModel?
    ) {
        jsonHistoryViewModel?.isShowLoading?.observe(viewLifecycleOwner) { isShown ->
        }
    }

    private fun handleObserverForNoHistory(viewModel: JSONViewModel?) {
        viewModel?.hasNoJSONHistory?.observe(viewLifecycleOwner) { bool ->
            if (bool) {
                Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "NOT Empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleObserverForHistoryList(viewModel: JSONViewModel?) {
        viewModel?.getJSONHistoryList?.observe(viewLifecycleOwner) { jsonValue ->
            println(jsonValue)
            //TODO: Handle the response here
//            val map: Map<String, List<Any>> =
//                Gson().fromJson(jsonValue, object : TypeToken<Map<String, List<Any>>>() {}.type)
            //Sample URL Parsing Here

            val imgUrl = ArrayList<String>()
            imgUrl.add("https://images.pexels.com/photos/277390/pexels-photo-277390.jpeg");
            imgUrl.add("https://images.pexels.com/photos/343720/pexels-photo-343720.jpeg");
            imgUrl.add("https://images.pexels.com/photos/984619/pexels-photo-984619.jpeg");
            imgUrl.add("https://images.pexels.com/photos/7974/pexels-photo.jpg");

            val adapter = Adapter(imgUrl, this@MainFragment)
            horizontalScrollView.setAdapter(adapter)
            horizontalScrollView.setLayoutManager(LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, true))
            adapter.notifyDataSetChanged()

        }
    }

    private fun handleObserverForError(view: View?, viewModel: JSONViewModel?) {
        viewModel?.getErrorMessage?.observe(viewLifecycleOwner) {
        }
    }
}

class Adapter(var urls: ArrayList<String>, var context: MainFragment) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val image: ImageView = v.findViewById(R.id.horizontalImg)
        val name: TextView = v.findViewById(R.id.horizontalText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.horizontal_list_item, parent, false)
//        v.layoutParams = RecyclerView.LayoutParams(1080, 800)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(this.context)
            .load(urls[position])
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return urls.size
    }
}