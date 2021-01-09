package com.test.movieslist.ui.movies

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.test.movieslist.R
import org.hamcrest.Matcher

open class KakaoTestScreen : Screen<KakaoTestScreen>() {
    val content: KView = KView { withId(R.id.content) }

    val moviesRecyclerView: KRecyclerView =
        KRecyclerView({ withId(R.id.moviesRecyclerView) }, itemTypeBuilder = {
            itemType(::MainItem)
        })

    class MainItem(parent: Matcher<View>) : KRecyclerItem<MainItem>(parent) {
        val imageViewPoster = KImageView(parent) { withId(R.id.image) }
        val percentage = KTextView(parent) { withId(R.id.percentage) }
        val title = KTextView(parent) { withId(R.id.title) }
    }
}
