scoreboard players add @s aj.ajnullpoint.animation.idle.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajnullpoint.animation.idle.local_anim_time
function animated_java:ajnullpoint/zzzzzzzz/animations/idle/apply_frame_as_root
execute if score @s aj.ajnullpoint.animation.idle.local_anim_time matches 120.. run function animated_java:ajnullpoint/zzzzzzzz/animations/idle/end