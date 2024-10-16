/*
 * Copyright © 2024 Shiva Thapa (@shivathapaa). All rights reserved.
 *
 * Licensed under the Mozilla Public License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://mozilla.org/MPL/2.0/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.shivathapaa.nepalidatepickerkmp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.shivathapaa.nepalidatepickerkmp.calendar_model.NepaliCalendarModel
import dev.shivathapaa.nepalidatepickerkmp.calendar_model.NepaliDatePickerColors
import dev.shivathapaa.nepalidatepickerkmp.data.CustomCalendar
import dev.shivathapaa.nepalidatepickerkmp.data.NepaliDatePickerLang
import dev.shivathapaa.nepalidatepickerkmp.data.toSimpleDate

@Composable
internal fun NepaliDateRangeInputContent(
    selectedStartDate: CustomCalendar?,
    selectedEndDate: CustomCalendar?,
    onDatesSelectionChange: (startNepaliCalendar: CustomCalendar?, endNepaliCalendar: CustomCalendar?) -> Unit,
    calendarModel: NepaliCalendarModel,
    yearRange: IntRange,
    language: NepaliDatePickerLang,
    nepaliSelectableDates: NepaliSelectableDates,
    colors: NepaliDatePickerColors
) {
    // Obtain the DateInputFormat for the default Locale.
    val errorInvalidMonthOrDay = "Month or day is incorrect. Please enter a valid date"
    val errorDateInvalidInput = "Day is invalid. Please recheck total days in month"
    val errorDateOutOfYearRange =
        "Date out of expected year range ${yearRange.first} - ${yearRange.last}"
    val errorInvalidNotAllowed = "Date is not allowed"
    val errorInvalidRange = "Date range input is invalid. Recheck allowed dates"
    val dateInputValidator =
        remember {
            NepaliDateInputValidator(
                yearRange = yearRange,
                nepaliSelectableDates = nepaliSelectableDates,
                errorInvalidMonthOrDay = errorInvalidMonthOrDay,
                errorDateInvalidInput = errorDateInvalidInput,
                errorDateOutOfYearRange = errorDateOutOfYearRange,
                errorInvalidNotAllowed = errorInvalidNotAllowed,
                errorInvalidRangeInput = errorInvalidRange
            )
        }
    // Apply both start and end dates for proper validation.
    dateInputValidator.apply {
        currentStartDate = selectedStartDate?.toSimpleDate()
        currentEndDate = selectedEndDate?.toSimpleDate()
    }
    Row(
        modifier = Modifier.padding(paddingValues = NepaliDateInputTextFieldPadding),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val startRangeText = language.startDate
        NepaliDateInputTextField(
            modifier = Modifier.weight(0.5f),
            calendarModel = calendarModel,
            label = { Text(startRangeText) },
            placeholder = { Text(PatternFormat) },
            initialSelectedDate = selectedStartDate,
            onDateSelectionChange = { startDate ->
                onDatesSelectionChange(startDate, selectedEndDate)
            },
            nepaliDateInputIdentifier = NepaliDateInputIdentifier.StartDateInput,
            nepaliDateInputValidator = dateInputValidator,
            language = language,
            colors = colors
        )

        val endRangeText = language.endDate
        NepaliDateInputTextField(
            modifier = Modifier.weight(0.5f),
            calendarModel = calendarModel,
            label = { Text(endRangeText) },
            placeholder = { Text(PatternFormat) },
            initialSelectedDate = selectedEndDate,
            onDateSelectionChange = { endDate ->
                onDatesSelectionChange(selectedStartDate, endDate)
            },
            nepaliDateInputIdentifier = NepaliDateInputIdentifier.EndDateInput,
            nepaliDateInputValidator = dateInputValidator,
            language = language,
            colors = colors
        )
    }
}