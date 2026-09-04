scoreboard players add @s aj.ajspellplane.animation.cast.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajspellplane.animation.cast.local_anim_time
function animated_java:ajspellplane/zzzzzzzz/animations/cast/apply_frame_as_root
execute if score @s aj.ajspellplane.animation.cast.local_anim_time matches 20.. run function animated_java:ajspellplane/zzzzzzzz/animations/cast/end