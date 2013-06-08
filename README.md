Chunkster
=========

A chunk buying and selling system for Bukkit.

Commands:
  - /chunk
  - /unchunk
  - /chm <flag> [move/build/container/chat]    //pvp will be added soon
  - /chm remove <owner/member> <username>
  - /chm add <owner/member> <username>
  - 
  
Rights:
  mainowner extends owner:
    - selling chunk
    - manage user <owner/member>
  
  owner extends member:
    - modify flags
  
  member:
    - build
    - chat
    - access container
    - move
