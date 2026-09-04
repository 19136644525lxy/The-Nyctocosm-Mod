#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[nbt={SelectedItem:{id:"minecraft:glass_bottle"}}] run function items:__generated__/block/2
execute as @s[tag=!collect_soul,nbt={SelectedItem:{id:"minecraft:carrot_on_a_stick",Count:1b,tag:{soul_bottle:1}}}] run function items:__generated__/block/3
execute as @s[tag=!collect_soul,predicate=spells:ring/wearing_ring] run function items:__generated__/block/4