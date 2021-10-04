package sanmi.labs.spotifyclone.model

data class Album(
    val name: String,
    private val images: List<Image>,
) {
    val image = images[0]
}
