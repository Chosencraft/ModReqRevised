package com.chosencraft.www.modreqrevised;

import com.chosencraft.purefocus.Chat;
import com.chosencraft.www.modreqrevised.utils.RequestState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Object class to construct ModReqs
 */
public class ModReq
{

    /**  Request number of the ID*/
    private int     requestID;

    /** UUID of requester*/
    private UUID requesterUUID;
    /** Name of requester */
    private String requester;

    /** UUID of the world where requester made the ModReq*/
    private UUID locationWorldUUID;
    /** X coord of ModReq location*/
    private int     locationX;
    /** Y coord of ModReq location*/
    private int     locationY;
    /** Z coord of ModReq location*/
    private int     locationZ;
    /** Message of the ModReq*/
    private String requestMessage;

    /** Request Owner UUID who took the task, null if none */
    private UUID taskOwnerUUID;
    /** Request Owner name who took the task, null if none */
    private String taskOwner;
    /** Request Owner who took the task, null if none */
    private String taskResolution;
    /** State of the request, ENUM*/
    private RequestState state;
    /** Timestamp of the request*/
    private Timestamp timestamp;


    /**
     * Public constructor to create a new ModReq
     * @param requesterUUID UUID of the requester
     * @param worldUUID UUID of the world the requester is in
     * @param locationX X Coordinate of where the requester is
     * @param locationY Y Coordinate of where the requester is
     * @param locationZ Z Coordinate of where the requester is
     * @param requestMessage Message made by the requester
     */
    public ModReq(String requesterName, UUID requesterUUID, UUID worldUUID, int locationX, int locationY, int locationZ, String requestMessage)
    {
        this.requesterUUID = requesterUUID;
        this.requester = requesterName;
        this.locationWorldUUID = worldUUID;
        this.locationX = locationX;
        this.locationY = locationY;
        this.locationZ = locationZ;

        this.requestMessage = requestMessage;

        this.state = RequestState.UNCLAIMED;
    }

    /**
     * Public constructor to recreate a ModReq from the database
     * @param requesterUUID UUID of the requester
     * @param worldUUID UUID of the world the requester is in
     * @param locationX X Coordinate of where the requester is
     * @param locationY Y Coordinate of where the requester is
     * @param locationZ Z Coordinate of where the requester is
     * @param requestMessage Message made by the requester
     */
    public ModReq(String requesterName, UUID requesterUUID, UUID worldUUID, int locationX, int locationY, int locationZ, String requestMessage,
                  String taskOwner, UUID taskOwnerUUID, String taskResolution, Timestamp timestamp, String requestState, int requestID)
    {
        this.requesterUUID = requesterUUID;
        this.requester = requesterName;

        this.locationWorldUUID = worldUUID;
        this.locationX = locationX;
        this.locationY = locationY;
        this.locationZ = locationZ;

        this.requestMessage = requestMessage;

        this.state = RequestState.UNCLAIMED;

        this.taskOwnerUUID = taskOwnerUUID;
        this.taskOwner = taskOwner;
        this.taskResolution = taskResolution;
        this.timestamp = timestamp;
        this.state = parseRequestState(requestState);
        this.requestID = requestID;
    }


    /**
     * Returns the ID of the ModReq
     * @return ID of the ModReq
     */
    public int getID()
    {
        return this.requestID;
    }


    /**
     * Sets the ID for the ModReq
     * @param id ID to set
     */
    public void setID(int id)
    {
        this.requestID = id;
    }


    /**
     * Returns state of the ModReq
     * @return RequestState of ModReq
     */
    public RequestState getState()
    {
        return this.state;
    }

    /**
     * Get Requester UUID
     * @return UUID of the requester
     */
    public UUID getRequesterUUID()
    {
        return this.requesterUUID;
    }

    /**
     * Retrieve Name of the requester
     * @return Name of the requester
     */
    public String getRequester()
    {
        return  this.requester;
    }

    /**
     * Retrieves set location
     * @return Location where the request was made
     */
    public Location getRequesterLocation()
    {
        return new Location(Bukkit.getWorld(this.locationWorldUUID), this.locationX, this.locationY, this.locationZ);
    }


    /**
     * Retrieve task owner name
     * @return name of the task owner
     */
    public String getTaskOwner()
    {
        return this.taskOwner;
    }

    /**
     * Retrieve the UUID of the task owner
     * @return UUID of the task owner
     */
    public UUID getTaskOwnerUUID()
    {
        return taskOwnerUUID;
    }

    /**
     * Update the task owner
     * @param taskOwner Name of the new task owner
     * @param taskOwnerUUID UUID of the new task owner
     */
    public void setTaskOwner(String taskOwner, UUID taskOwnerUUID)
    {
        if (taskOwner == null || taskOwnerUUID == null)
        {
            this.state = RequestState.UNCLAIMED;
            return;
        }

        if (this.state == RequestState.UNCLAIMED)
        {
            this.state = RequestState.CLAIMED;
        }

        this.taskOwnerUUID  = taskOwnerUUID;
        this.taskOwner = taskOwner;
    }


    /**
     * Return request message of the request
     * @return The request message
     */
    public String getRequestMessage()
    {
        return this.requestMessage;
    }

    /**
     * Retrieve the timestamp of the request
     * @return Timestamp of request at creation
     */
    public Timestamp getTimestamp()
    {
        return this.timestamp;
    }

    /**
     * Retrieve the resolution of the ModReq
     * @return The resolution of the ModReq
     */
    public String getTaskResolution()
    {
        return this.taskResolution;
    }

    /**
     * Complete the ModReq without a resolution message
     */
    public void completeTask()
    {
        this.state = RequestState.FINISHED;
    }

    /**
     * Complete the ModReq with a resolution message
     * @param taskResolution Message of the task resolution
     */
    public void completeTask(String taskResolution)
    {
        this.state = RequestState.FINISHED;
        this.taskResolution = taskResolution;
    }

    /**
     * Parses the string request into a RequestState Enum
     * @param requestState state to be parsed
     * @return The Request state parsed
     */
    private RequestState parseRequestState(String requestState)
    {
        switch (requestState.toUpperCase())
        {
            case "FINISHED":
                return RequestState.FINISHED;
            case "CLAIMED":
                return RequestState.CLAIMED;
            case "UNCLAIMED":
                return RequestState.UNCLAIMED;
            default:
                // something happened to it, don't know what
                return RequestState.ORPHANED;
        }
    }

    public String getFormattedSummary()
    {
        StringBuilder sb = new StringBuilder();

            boolean isOnline = false;
            Player p = Bukkit.getPlayerExact(this.getRequester());
            if (p != null && p.isOnline())
            {
                sb.append("&6#").append(this.requestID).append(" &a").append(this.getRequester()).append(" &7- ");
            } else
            {

                sb.append("&6#").append(this.requestID).append("&c").append(this.getRequester()).append(" &7- ");
            }


        if (state.equals(RequestState.CLAIMED))
        {
            sb.append("&dClaimed by ").append(this.getTaskOwner());
            return Chat.format(sb.toString());
        }

        return Chat.format(Chat.trim(sb.toString(), this.requestMessage));
    }

    /**
     * String format of the ModReq
     * @return Formatted string
     */
    public String toString()
    {
        return String.format("Request [%d, %s, %s]", requestID, state.toString(), requester);
    }
}
