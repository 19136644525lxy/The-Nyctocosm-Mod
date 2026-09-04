scoreboard players add @s aj.ajhypertrace.animation.idle.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajhypertrace.animation.idle.local_anim_time
function animated_java:ajhypertrace/zzzzzzzz/animations/idle/apply_frame_as_root
execute if score @s aj.ajhypertrace.animation.idle.local_anim_time matches 20.. run function animated_java:ajhypertrace/zzzzzzzz/animations/idle/end