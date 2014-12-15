/*
 * Copyright (C) 2014 Morten Laukvik <morten@laukvik.no>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
angular.module("timeTravelApp", [])
        .controller('timeController', function ($scope, $http) {
            $scope.eventFromYear = null;
            $scope.eventToYear = null;
            $scope.eventViewMode = 1;
            $scope.selectedTags = null;
            $scope.developerMode = false;

            var EVENTS_URL_JSON = "json/events.json";
            var EVENTS_URL_REST = "webresources/events/findevents";

            var TAGS_URL_JSON = "json/tags.json";
            var TAGS_URL_REST = "webresources/events/findtags";

            var ERAS_URL_JSON = "json/eras.json";
            var ERAS_URL_REST = "webresources/events/finderas";

            var COLL_URL_JSON = "json/collections.json";
            var COLL_URL_REST = "webresources/events/findcollections";

            var EVENTS_URL = $scope.developerMode ? EVENTS_URL_JSON : EVENTS_URL_REST;
            var TAGS_URL = $scope.developerMode ? TAGS_URL_JSON : TAGS_URL_REST;
            var ERAS_URL = $scope.developerMode ? ERAS_URL_JSON : ERAS_URL_REST;
            var COLL_URL = $scope.developerMode ? COLL_URL_JSON : COLL_URL_REST;


            $scope.selectTag = function (tag, item) {
//                item.className += " active";
            };

            $scope.selectEra = function (era, item) {
            };

            $scope.selectGeo = function (geo, item) {
            };

            $http.get('json/messages_no.json')
                    .then(function (res) {
                        $scope.messages = res.data;
                    });

            $http.get(EVENTS_URL)
                    .then(function (res) {
                        $scope.eventsJson = res.data;
                    });

            $http.get(TAGS_URL)
                    .then(function (res) {
                        $scope.tagsJson = res.data;
                    });

            $http.get(ERAS_URL)
                    .then(function (res) {
                        $scope.erasJson = res.data;
                    });
            $http.get(COLL_URL)
                    .then(function (res) {
                        $scope.collectionsJson = res.data;
                    });
        });
