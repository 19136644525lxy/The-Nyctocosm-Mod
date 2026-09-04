execute if score @s aj.loop_mode = $aj.loop_mode.loop aj.i run scoreboard players set @s aj.animation.walk.local_anim_time 0
execute if score @s aj.loop_mode = $aj.loop_mode.once aj.i run function death_walker:animations/walk/stop
execute if score @s aj.loop_mode = $aj.loop_mode.hold aj.i run function death_walker:animations/walk/pause