/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.touringmusician;


import android.graphics.Point;

import java.util.Iterator;

public class CircularLinkedList implements Iterable<Point> {

    private class Node {
        Point point;
        Node prev, next;
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        Node(Point point,Node prev,Node next) {
            this.point = point;
            this.prev = prev;
            this.next = next;
        }
    }

    Node head = null;

    public void insertBeginning(Point p) {
        if(head == null) {
            head = new Node(p, null, null);
            head.prev = head;
            head.next = head;
        } else {
            Node prevNode = head.prev;
            Node temp = head;
            Node node = new Node(p,prevNode,head);
            prevNode.next = node;
            temp.prev = node;
            head = node;
        }
    }

    private float distanceBetween(Point from, Point to) {
        return (float) Math.sqrt(Math.pow(from.y-to.y, 2) + Math.pow(from.x-to.x, 2));
    }

    public float totalDistance() {
        float total = 0;
        CircularLinkedListIterator iterator = new CircularLinkedListIterator();
        Point prevPoint = iterator.next();
        Point nextPoint;
        int counter = 0;
        while (iterator.current != null) {
            nextPoint = iterator.next();
            total += distanceBetween(prevPoint, nextPoint);
            prevPoint = nextPoint;
            counter++;
        }
        if (counter > 1) {
            total += distanceBetween(head.point, prevPoint);
        }
        return total;
    }

    public void insertNearest(Point p) {
        if(head == null) {
            head = new Node(p, null, null);
            head.prev = head;
            head.next = head;
        } else {
            CircularLinkedListIterator iterator = new CircularLinkedListIterator();
            Node currNearestNode = iterator.current;
            while (iterator.hasNext()) {
                Node iteratorNode = iterator.current;
                if (distanceBetween(p, iteratorNode.point) < distanceBetween(p, currNearestNode.point)) {
                    currNearestNode = iteratorNode;
                }
                iterator.next();
            }
            Node temp = currNearestNode;
            Node prevNode = temp.prev;
            Node node = new Node(p, prevNode, temp);
            temp.prev = node;
            prevNode.next = node;
            if (head == currNearestNode) {
                head = node;
            }
        }
    }

    public void insertSmallest(Point p) {
        if(head == null) {
            head = new Node(p, null, null);
            head.prev = head;
            head.next = head;
        } else {
            CircularLinkedListIterator iterator = new CircularLinkedListIterator();
            Node currNearestNode = iterator.current;
            if (head.next == head) {
                Node node = new Node(p, head, head);
                head.next = node;
                head.prev = node;
                return;
            }
            while (iterator.hasNext()) {
                Node iteratorNode = iterator.current;
                if (distanceBetween(p, iteratorNode.point) < distanceBetween(p, currNearestNode.point)) {
                    currNearestNode = iteratorNode;
                }
                iterator.next();
            }
            Node currSecondNearNode = currNearestNode.prev;
            if (distanceBetween(currNearestNode.point, currSecondNearNode.point) + distanceBetween(p, currNearestNode.next.point) <
            distanceBetween(currSecondNearNode.point, p) + distanceBetween(currNearestNode.point, currNearestNode.next.point)) {
                Node temp = currNearestNode.next;
                Node node = new Node(p, currNearestNode, temp);
                temp.prev = node;
                currNearestNode.next = node;
                if (currNearestNode == head) {
                    head = node;
                }
            } else {
                Node node = new Node(p, currSecondNearNode, currNearestNode);
                currNearestNode.prev = node;
                currSecondNearNode.next = node;
                if (currNearestNode == head) {
                    head = node;
                }
            }
        }
    }

    public void reset() {
        head = null;
    }

    private class CircularLinkedListIterator implements Iterator<Point> {

        Node current;

        public CircularLinkedListIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public Point next() {
            Point toReturn = current.point;
            current = current.next;
            if (current == head) {
                current = null;
            }
            return toReturn;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<Point> iterator() {
        return new CircularLinkedListIterator();
    }


}
