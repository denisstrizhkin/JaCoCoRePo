package com.example.jacocorepo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.jacocorepo.databinding.ActivityMainBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var disposeBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        disposeBag.add(RxTextView.textChanges(binding.firstEditText)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .map { it.toString().lowercase() }
            .subscribe { text ->
                Log.i("MyTag", text.toString())
            }
        )

        disposeBag.add(RxTextView.textChanges(binding.secondEditText)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .map { it.toString().lowercase() }
            .subscribe { text ->
                Log.i("MyTag", text.toString())
            }
        )

        binding.buttonNext.setOnClickListener {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
    }
}