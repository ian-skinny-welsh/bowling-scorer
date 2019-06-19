package scorer

enum class FrameType {
    SIMPLE_FRAME {
        override fun isThisType(frameStr: String): Boolean {
            return frameStr.matches("[1-9-]{2}".toRegex())
        }
    },
    SPARE_FRAME {
        override fun isThisType(frameStr: String): Boolean {
            return frameStr.length == 2 && frameStr.last() == '/'
        }
    },
    STRIKE_FRAME {
        override fun isThisType(frameStr: String): Boolean {
            return frameStr.length == 1 && frameStr == "X"
        }
    };

    abstract fun isThisType(frameStr: String): Boolean
}