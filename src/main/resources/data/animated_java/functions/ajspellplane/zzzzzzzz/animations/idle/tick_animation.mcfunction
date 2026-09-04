scoreboard players add @s aj.ajspellplane.animation.idle.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajspellplane.animation.idle.local_anim_time
function animated_java:ajspellplane/zzzzzzzz/animations/idle/apply_frame_as_root
execute if score @s aj.ajspellplane.animation.idle.local_anim_time matches 400.. run function animated_java:ajspellplane/zzzzzzzz/animations/idle/end