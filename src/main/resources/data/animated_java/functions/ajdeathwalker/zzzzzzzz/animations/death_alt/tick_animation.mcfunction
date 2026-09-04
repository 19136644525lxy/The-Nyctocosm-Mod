scoreboard players add @s aj.ajdeathwalker.animation.death_alt.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajdeathwalker.animation.death_alt.local_anim_time
function animated_java:ajdeathwalker/zzzzzzzz/animations/death_alt/apply_frame_as_root
execute if score @s aj.ajdeathwalker.animation.death_alt.local_anim_time matches 65.. run function animated_java:ajdeathwalker/zzzzzzzz/animations/death_alt/end