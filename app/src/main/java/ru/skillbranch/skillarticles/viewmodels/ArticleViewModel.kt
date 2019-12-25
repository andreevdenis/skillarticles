package ru.skillbranch.skillarticles.viewmodels

import androidx.lifecycle.LiveData
import ru.skillbranch.skillarticles.data.ArticleData
import ru.skillbranch.skillarticles.data.ArticlePersonalInfo
import ru.skillbranch.skillarticles.data.repositories.ArticleRepository
import ru.skillbranch.skillarticles.extensions.data.toAppSettings
import ru.skillbranch.skillarticles.extensions.format

class ArticleViewModel (private val articleId:String): BaseViewModel<ArticleState>(ArticleState()) {
    private val repository = ArticleRepository


    init {
        subscribeOnDataSource(getArticleData()){ article, state ->
            article ?: return@subscribeOnDataSource null
            state.copy(
                shareLink = article.shareLink,
                title = article.title,
                category = article.category,
                categoryIcon = article.categoryIcon,
                date = article.date.format()

            )
        }

        subscribeOnDataSource(getArticleContent()){ content, state ->
            content ?: return@subscribeOnDataSource null
            state.copy(
                isLoadingContent = false,
                content = content
            )
        }

        subscribeOnDataSource(getArticlePersonalInfo()) { info, state ->
            info ?: return@subscribeOnDataSource null
            state.copy(
                isBookmark = info.isBookmark,
                isLike = info.isLike
            )
        }

        subscribeOnDataSource(repository.getAppSettings()) { settings, state ->
            settings ?: return@subscribeOnDataSource null
            state.copy(
                isDarkMode = settings.isDarkMode,
                isBigText = settings.isBigText

            )
        }
    }

    /**
     * Получение полной информации о статье из сети
     * (или базы данных если она сохранена, наличие статьи в базе не надо реализовывать в данном уроке)
     */
    private fun getArticleContent(): LiveData<List<Any>?>{
        return repository.loadArticleContent(articleId)
    }

    /**
     * Получение краткой информации о статье из базы данных
     */
    private fun getArticleData(): LiveData<ArticleData?>{
        return repository.getArticle(articleId)
    }


    /**
     * Получение пользовательской информации о статье из базы данных
     */
    private fun getArticlePersonalInfo(): LiveData<ArticlePersonalInfo?>{
        return repository.loadArticlePersonalInfo(articleId)
    }


    /**
     * Получение настроек приложения
     */
    fun handleNightMode()
    {
        val settings = currentState.toAppSettings()
        repository.updateSettings(settings.copy(isDarkMode = !settings.isDarkMode))
    }


    /**
     * Обработка нажатия на btn_text_up (увеличение шрифта текста)
     * необходимо увеличить шрифт до значения 18
     */
    fun handleUpText()
    {
        repository.updateSettings(currentState.toAppSettings().copy(isBigText = true))
    }

    /**
     * Обработка нажатия на btn_text_down (стандартный размер шрифта)
     * необходимо установить размер шрифта по умолчанию 14
     */
    fun handleDownText()
    {
        repository.updateSettings(currentState.toAppSettings().copy(isBigText = false))
    }

    /**
     * добавление/удалние статьи в закладки, обрабока нажатия на кнопку btn_bookmark
     * необходимо отобразить сообщение пользователю "Add to bookmarks" или "Remove from bookmarks"
     * в соответствии с текущим состоянием
     */
    fun handleBookmark(){

    }

    /**
     * добавление/удалние статьи в понравившееся, обрабока нажатия на кнопку btn_like
     * необходимо отобразить сообщение пользователю (Notify.ActionMessage) "Mark is liked" или
     * "Don`t like it anymore"  в соответствии с текущим состоянием.
     * если пользователь убрал Like необходимо добавить  actionLabel в снекбар
     * "No, still like it" при нажатиии на который состояние вернется к isLike = true
     */
    fun handleLike(){

    }

    /**
     * поделиться статьей, обрабока нажатия на кнопку btn_share
     * необходимо отобразить сообщение с ошибкой пользователю (Notify.ErrorMessage) "Share is not implemented"
     * и текстом errLabel "OK"
     */
    fun handleShare(){
        notify(Notify.ErrorMessage("Share is not implemented","OK", null))
    }

    /**
     * обрабока нажатия на кнопку btn_settings
     * необходимо отобразить или скрыть меню в соответствии с текущим состоянием
     */
    fun handleToggleMenu(){
        updateState { it.copy(isShownMenu = !it.isShownMenu) }
    }

    /**
     * обрабока перехода в режим поиска searchView
     * при нажатии на пункту меню тулбара необходимо отобразить searchView и сохранить состояние при
     * изменении конфигурации (пересоздании активити)
     */
    fun handleSearchMode(isSearch: Boolean){

    }

    /**
     * обрабока поискового запроса, необходимо сохранить поисковый запрос и отображать его в
     * searchView при изменении конфигурации (пересоздании активити)
     */
    fun handleSearch(query: String?){

    }
}

data class ArticleState(
    val isAuth: Boolean = false,
    val isLoadingContent: Boolean = true,
    val isLoadingReviews: Boolean = true,
    val isLike: Boolean = false,
    val isBookmark: Boolean = false,
    val isShownMenu: Boolean = false,
    val isBigText: Boolean = false,
    val isDarkMode: Boolean = false,
    val isSearch: Boolean = false,
    val searchQuery: String? = null,
    val searchResults: List<Pair<Int, Int>> = emptyList(),
    val searchPosition: Int = 0,
    val shareLink: String? = null,
    val title: String? = null,
    val category: String? = null,
    val categoryIcon: Any? = null,
    val date: String? = null,
    val author: Any? = null,
    val poster: String? = null,
    val content: List<Any> = emptyList(),
    val reviews: List<Any> = emptyList()
)