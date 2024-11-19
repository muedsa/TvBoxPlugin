package com.muedsa.tvbox.api.data

data class MediaCatalogOption(
    val name: String,
    val value: String,
    val items: List<MediaCatalogOptionItem>,
    val required: Boolean = false,
    val multiple: Boolean = false,
) {

    companion object {
        fun getDefault(options: List<MediaCatalogOption>): List<MediaCatalogOption> =
            options.filter { option -> option.required }
                .map {
                    it.copy(
                        items = if (it.multiple) {
                            listOf(it.items.find { item -> item.defaultChecked }!!)
                        } else {
                            it.items.filter { item -> item.defaultChecked }
                        }
                    )
                }
    }
}
