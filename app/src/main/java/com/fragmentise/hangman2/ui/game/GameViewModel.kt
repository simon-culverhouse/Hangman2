package com.fragmentise.hangman2.ui.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking



class GameViewModel : ViewModel() {
    // Timer setUp
    companion object {
        // These represent different important times in the game, such as game length.

        // This is when the game is over
        private const val DONE = 0L

        // This is the time when the phone will start buzzing each second
        private const val COUNTDOWN_PANIC_SECONDS = 10L

        // This is the number of milliseconds in a second
        private const val ONE_SECOND = 1000L

        // This is the total time of the game
        private const val COUNTDOWN_TIME = 60000L

    }

    private lateinit var timer: CountDownTimer

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    // The String version of the current time
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    // set up live data
    private val _categoryText = MutableLiveData<String>()
    val categoryText: LiveData<String>
    get() = _categoryText

    private val _hide = MutableLiveData<List<Boolean>>()
    val hide: LiveData<List<Boolean>>
        get() = _hide

    private val _hangStage = MutableLiveData<Int>()
    val hangStage: LiveData<Int>
        get() = _hangStage

    private val _answerText = MutableLiveData<String>()
    val answerText: LiveData<String>
        get() = _answerText

    private val _correctText = MutableLiveData<Boolean>(false)
    val correctText: LiveData<Boolean>
        get() = _correctText

    private val _correctWord = MutableLiveData<Boolean>(false)
    val correctWord: LiveData<Boolean>
        get() = _correctWord

    // set up variables
    private lateinit var categoryList:MutableList<String>
    private lateinit var wordList: MutableList<String>
    private val hideList = mutableListOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)
    private val solutionList = mutableListOf("_", "_", "_", "_", "_", "_", "_")
    private val solutionCheck = mutableListOf("_", "_", "_", "_", "_", "_", "_")
    private var x = 3
    private var correctLetters = 0
    private var incorrectLetters = 0
    private var f = ""

    private val letterList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private var blank = "_" + "_" + "_" + "_"

    // choose the word category
    private fun chooseCategory(){
        categoryList = mutableListOf(
            "Animals",
            "Cities",
            "Planets"
        )
        categoryList.shuffle()

    }

    // setup list of words
    private fun setList(string: String) {
        when (string){
            "Animals" -> wordList = mutableListOf(
                "DOG",
                "CAT",
                "PANDA",
                "SNAIL",
                "FOX",
                "COW",
                "CHICKEN"
            )
            "Cities" -> wordList = mutableListOf(
                "LONDON",
                "MADRID",
                "PARIS"
            )
            "Planets" -> wordList = mutableListOf(
                "EARTH",
                "MARS",
                "SATURN"
            )
        }

        wordList.shuffle()
    }


    // execute on finish
    private fun onFinish() {
        Log.i("GameViewModel", "GameViewModel destroyed!")
        //_text.value = "The Game Has Changed"
        _answerText.value = "Game Over"
    }
    // check letters on click
    fun letterClick(string: String){
        println("letter = $string")

        checkLetter(string)

        for (letter in 0 until 26) {
            println(letter)
            if (letterList[letter].toString() == string) {

                var index = letter
                hideList.set(index, true)
                _hide.value = hideList

            }
        }
        _hangStage.value = incorrectLetters

        if (correctLetters == x) showCorrectPop()
        if (_hangStage.value == 11) nextWord()

        }

   // initialise at start up
    init {

        _categoryText.value = ""
        _answerText.value = ""
        _hangStage.value = 0
        chooseCategory()
        nextWord()
        timerStart()


    }

    private fun timerStart(){
        // Creates a timer which triggers the end of the game when it finishes
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
                if (millisUntilFinished / ONE_SECOND <= COUNTDOWN_PANIC_SECONDS) {

                }
            }

            override fun onFinish() {
                _currentTime.value = DONE

            }
        }

        timer.start()
    }

    fun nextWord() {

        clear()
        //select category
        if (categoryList.isEmpty()) {
            chooseCategory()
        }else {
            val category = categoryList.get(0)
            categoryList.removeAt(0)
            _categoryText.value = category
            setList(category)
        }

        // select next word
        if (wordList.isEmpty()) {
            val category = categoryList.get(0)
            setList(category)

        } else {
            val currentWord = wordList.get(0)
            wordList.removeAt(0)
            // Assign word length to x
            x = currentWord.length

            for (letter in 0 until (x)){
                println(x)

                println(letter)
                solutionList[letter] = currentWord[letter].toString()
                solutionCheck[letter] = "_"
            }

        }
        set()
    }

    // Verify if letter pressed is in word
    private fun checkLetter(letter: String) {
        val holdValue = correctLetters
        for (check in 0 until x) {
            if (letter == solutionList[check]) {
                solutionCheck[check] = solutionList[check]
                correctLetters ++
            }
        }
        if (holdValue == correctLetters) incorrectLetters ++
        f = ""
        when (x) {
            3 -> f = solutionCheck[0] + " " + solutionCheck[1] + " " + solutionCheck[2]
            4 -> f = solutionCheck[0] + " " + solutionCheck[1] + " " + solutionCheck[2] + " " + solutionCheck[3]
            5 -> f = solutionCheck[0] + " " + solutionCheck[1] + " " + solutionCheck[2] + " " + solutionCheck[3] + " " + solutionCheck[4]
            6 -> f = solutionCheck[0] + " " + solutionCheck[1] + " " + solutionCheck[2] + " " + solutionCheck[3] + " " + solutionCheck[4] + " " + solutionCheck[5]
            7 -> f = solutionCheck[0] + " " + solutionCheck[1] + " " + solutionCheck[2] + " " + solutionCheck[3] + " " + solutionCheck[4] + " " + solutionCheck[5] + " " + solutionCheck[6]

        }
        _answerText.value = f

        println(f)

    }
    // clear values
    private fun clear() {
        for (f in 0 until x-1) {
            solutionList[f] = "_"
            solutionCheck[f] = "_"
        }
        booleanList()
        timerStart()
    }
    // reset values
    private fun set(){
        when (x){
            3 -> blank = "_ _ _"
            4 -> blank = "_ _ _ _"
            5 -> blank = "_ _ _ _ _"
            6 -> blank = "_ _ _ _ _ _"
            7 -> blank = "_ _ _ _ _ _ _"
        }
        _answerText.value = blank
        correctLetters = 0
        incorrectLetters = 0
        _hangStage.value = 0





    }
    // set up mutable boolean list
    private fun booleanList(){
        for (item in 0..25){
            hideList.set(item, false)
        }
        _hide.value = hideList
    }

    private fun showCorrectPop (){

        timer.cancel()
        _correctWord.value = true
//        runBlocking {
//            delay(1000)
//        }
        _correctText.value = true




        //nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

}