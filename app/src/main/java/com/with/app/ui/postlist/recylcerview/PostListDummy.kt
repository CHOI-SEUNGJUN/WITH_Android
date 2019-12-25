package com.with.app.ui.postlist.recylcerview

import com.with.app.data.PostItem


internal class PostListDummy {
    fun PostList() : List<PostItem>{
        return listOf(
            PostItem(
                region = "남아프리카공화국",
                date = "20.02.02 ~ 20.03.08",
                time = "1분 전",
                title = "런던에서 같이 레미제라블 보실 분",
                participant = "5"
            ),
            PostItem(
                region = "남아프리카공화국",
                date = "20.02.02 ~ 20.03.08",
                time = "1분 전",
                title = "런던에서 같이 레미제라블 보실 분",
                participant = "5"
            ),
            PostItem(
                region = "남아프리카공화국",
                date = "20.02.02 ~ 20.03.08",
                time = "1분 전",
                title = "런던에서 같이 레미제라블 보실 분",
                participant = "5"
            )
        )
    }

}