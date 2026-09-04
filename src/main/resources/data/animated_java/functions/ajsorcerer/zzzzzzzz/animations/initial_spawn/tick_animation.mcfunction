scoreboard players add @s aj.ajsorcerer.animation.initial_spawn.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajsorcerer.animation.initial_spawn.local_anim_time
function animated_java:ajsorcerer/zzzzzzzz/animations/initial_spawn/apply_frame_as_root
execute if score @s aj.ajsorcerer.animation.initial_spawn.local_anim_time matches 331.. run function animated_java:ajsorcerer/zzzzzzzz/animations/initial_spawn/end