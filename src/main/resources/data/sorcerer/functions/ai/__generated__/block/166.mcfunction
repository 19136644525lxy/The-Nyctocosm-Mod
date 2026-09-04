#built using mc-build (https://github.com/mc-build/mc-build)

execute unless score Sorcerer ns.attacking matches 1.. if score SorcererIdle value matches 1.. if score Sorcerer ns.attackcooldown matches 1.. run scoreboard players remove Sorcerer ns.attackcooldown 1
execute if score SorcererIdle value matches 1 unless score Sorcerer ns.attackcooldown matches 1.. run function sorcerer:ai/__generated__/block/167