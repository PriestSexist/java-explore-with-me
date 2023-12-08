package ru.practicum.ewmserver.error.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorStrings {
    public static final String TOO_SHORT = "String too short";
    public static final String TOO_LONG = "String too long";
    public static final String COMPILATION_NOT_FOUND_BY_ID = "Compilation with id=%d was not found";
    public static final String COMPILATION_NOT_FOUND_BY_TITLE = "Compilation with title=%s was not found";
    public static final String USER_WITH_THIS_EMAIL_ALREADY_EXISTS = "Email already exists";
    public static final String USER_NOT_FOUND_BY_ID = "User with id=%d was not found";
    public static final String REQUEST_NOT_FOUND_BY_ID = "Request with id=%d was not found";
    public static final String EVENT_NOT_FOUND_BY_ID = "Event with id=%d was not found";
    public static final String CATEGORY_NOT_FOUND_BY_ID = "Category with id=%d was not found";
    public static final String CANNOT_PUBLISH_EVENT_NOT_PENDING = "Cannot publish the event because it's not in the right state: PENDING";
    public static final String CANNOT_REJECT_EVENT_NOT_PENDING = "Cannot reject the event because it's not in the right state: PENDING";
    public static final String CANNOT_PUBLISH_EVENT_LESS_THEN_ONE_HOUR_ON_SITE = "Cannot publish the event because the event date should be at least an hour after publication";
    public static final String CATEGORY_WITH_THIS_NAME_ALREADY_EXISTS = "Category with this name already exists";
    public static final String INVALID_ACTION = "Invalid action: ";
    public static final String INVALID_STATE = "Invalid state: ";
    public static final String INVAlID_TIME_PARAMETERS = "Invalid time parameters";
    public static final String INVALID_SORTING_PARAMETERS = "Invalid sorting argument. Can only be one of [EVENT_DATE, VIEWS] or empty";
    public static final String REQUEST_ALREADY_EXISTS = "Request from user with id=%d to event with id=%d already exists";
    public static final String REQUEST_FROM_OWNER = "Can't create request. User with id=%d is owner of the event with %d";
    public static final String REQUEST_FOR_NOT_PUBLISHED_EVENT = "Can't create request. Event with id=%d is not published";
    public static final String EVENT_IS_FULL = "Can't create request. Event with id=%d is full";
    public static final String CANT_CANCEL_NOT_OWNER = "Can't cancel because you're not owner";
    public static final String EVENT_DATE_2_HOURS_MIN_SHOULD_BE = "Field: eventDate. Error: должно содержать дату, которая еще не наступила. Value: ";
    public static final String PATCH_NOT_PENDING_STATE = "Only pending or canceled events can be changed";
}
