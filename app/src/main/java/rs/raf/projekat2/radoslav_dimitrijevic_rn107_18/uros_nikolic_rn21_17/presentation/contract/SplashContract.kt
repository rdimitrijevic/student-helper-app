package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.presentation.contract

interface SplashContract {
    interface ViewModel {

        /**
         * Checks whether a user is logged in or not
         *
         * @return True if a user is logged in, false otherwise
         */
        fun isLoggedIn(): Boolean
    }
}