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
            $scope.eventDetailsMode = false;
            $http.get('json/events.json')
                    .then(function (res) {
                        $scope.eventsJson = res.data;
                    });
            $http.get('json/messages_no.json')
                    .then(function (res) {
                        $scope.messages = res.data;
                    });
        });
