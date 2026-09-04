scoreboard players add @s aj.ajmobilespellplane.animation.cast.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajmobilespellplane.animation.cast.local_anim_time
function animated_java:ajmobilespellplane/zzzzzzzz/animations/cast/apply_frame_as_root
execute if score @s aj.ajmobilespellplane.animation.cast.local_anim_time matches 23.. run function animated_java:ajmobilespellplane/zzzzzzzz/animations/cast/end