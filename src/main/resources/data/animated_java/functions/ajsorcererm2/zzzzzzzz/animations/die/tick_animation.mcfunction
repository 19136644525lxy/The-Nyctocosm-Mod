scoreboard players add @s aj.ajsorcererm2.animation.die.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajsorcererm2.animation.die.local_anim_time
function animated_java:ajsorcererm2/zzzzzzzz/animations/die/apply_frame_as_root
execute if score @s aj.ajsorcererm2.animation.die.local_anim_time matches 180.. run function animated_java:ajsorcererm2/zzzzzzzz/animations/die/end