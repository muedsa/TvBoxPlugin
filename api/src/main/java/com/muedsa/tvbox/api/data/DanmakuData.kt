package com.muedsa.tvbox.api.data

/**
 * @see <a href="https://github.com/KwaiAppTeam/AkDanmaku/blob/master/library/src/main/java/com/kuaishou/akdanmaku/data/DanmakuItemData.kt">DanmakuItemData.kt</a>
 */
data class DanmakuData(
    val danmakuId: Long,                // 单个弹幕的唯一 Id
    val position: Long,                 // 弹幕的相对时间戳，单位：毫秒
    val content: String,                // 弹幕文本内容
    val mode: Int = 1,                  // 弹幕的局排版的方式 0 1 4 5
    val textColor: Int = 0xFF_FF_FF,    // 弹幕字体颜色
    val score: Int = 9,                 // 弹幕的分值标识，用于排序计算等
    val danmakuStyle: Int = 1,          // 弹幕定制样式 1 2 4 8
)
