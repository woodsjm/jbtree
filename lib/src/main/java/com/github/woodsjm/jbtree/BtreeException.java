package com.github.woodsjm.jbtree;


class BtreeException {

    static class NodeIndexException extends Exception {
        NodeIndexException(String message) {
            super(message);
        }
    }

    static class NodeModifyException extends Exception {
        NodeModifyException(String message) {
            super(message);
        }
    }

    static class NodeNotFoundException extends Exception {
        NodeNotFoundException(String message) {
            super(message);
        }
    }

    static class NodeReferenceException extends Exception {
        NodeReferenceException(String message) {
            super(message);
        }
    }

    static class NodeTypeException extends Exception {
        NodeTypeException(String message) {
            super(message);
        }
    }

    static class NodeValueException extends Exception {
        NodeValueException(String message) {
            super(message);
        }
    }

    static class TreeHeightException extends Exception {
        TreeHeightException(String message) {
            super(message);
        }
    }
}