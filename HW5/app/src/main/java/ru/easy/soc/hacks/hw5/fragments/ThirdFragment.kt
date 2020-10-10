package ru.easy.soc.hacks.hw5.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.view.*
import ru.easy.soc.hacks.hw5.R
import ru.easy.soc.hacks.hw5.extensions.navigate

class ThirdFragment() : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.counter.text = ThirdFragmentArgs.fromBundle(requireArguments()).count.toString()

        view.counterAdder.setOnClickListener {
            navigate(ThirdFragmentDirections.actionThirdFragmentSelf(view.counter.text.toString().toInt() + 1))
        }
    }
}