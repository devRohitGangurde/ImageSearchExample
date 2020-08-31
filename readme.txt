Implementation Details :

ImageListScreen(Kotlin)
    - Here user list fetch from API and show in form of grids
   
ImageDetailsScreen(Java)
    - ImageDetails screen show with User Image and card-view
    - User can able to add comments on fullscreen image,
      comments save in database next time retrieval if any previous comment


#In Project
1. One Activity Code in Java & One In Kotlin as per requirement
2. Language Used - Kotlin, Java
3. Architecture component:
  a. MVVM Architecture Used
  b. Data binding + LiveData + ViewModel used
  c. Coroutines used for - Debounce search for 250ms
  d. Room - Database used for save comments
4. Design - Material Design Component used -like Toolbar, Theme, CardView etc
5. Networking -For API call Retrofit library used



