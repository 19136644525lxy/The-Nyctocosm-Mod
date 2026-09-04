scoreboard players add @s aj.ajdeathwalker.animation.death.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajdeathwalker.animation.death.local_anim_time
function animated_java:ajdeathwalker/zzzzzzzz/animations/death/apply_frame_as_root
execute if score @s aj.ajdeathwalker.animation.death.local_anim_time matches 41.. run function animated_java:ajdeathwalker/zzzzzzzz/animations/death/end